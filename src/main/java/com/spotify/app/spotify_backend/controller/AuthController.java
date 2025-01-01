package com.spotify.app.spotify_backend.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.spotify.app.spotify_backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;


@RestController
@RequestMapping("/auth/spotify")
public class AuthController {

    // Inyección de valores desde application.properties
    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    // Inyección del TokenService para manejar los tokens
    @Autowired
    private TokenService tokenService;

    // Endpoint para redirigir al usuario a Spotify
    @GetMapping("/login")
    public ResponseEntity<String> login() {
        String authorizationUrl = "https://accounts.spotify.com/authorize?" +
                "client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri +
                "&scope=user-top-read playlist-read-private user-library-read";

        return ResponseEntity.status(302).header(HttpHeaders.LOCATION, authorizationUrl).build();
    }

    // Callback para manejar el authorization code
    @GetMapping("/callback")
    public ResponseEntity<String> callback(@RequestParam String code) {
        RestTemplate restTemplate = new RestTemplate();

        String tokenUrl = "https://accounts.spotify.com/api/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        String body = "grant_type=authorization_code&code=" + code + "&redirect_uri=" + redirectUri;

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        // Extraer el access_token y refresh_token de la respuesta JSON
        String responseBody = response.getBody();
        String accessToken = extractAccessToken(responseBody);
        String refreshToken = extractRefreshToken(responseBody);

        // Obtener el id único del usuario desde Spotify
        String userId = getSpotifyUserId(accessToken);

        // Guardar los tokens en el servicio
        tokenService.saveTokens(userId, accessToken, refreshToken);

        return ResponseEntity.ok("Tokens guardados correctamente para el usuario: " + userId);
    }

    // Método para refrescar el access_token usando el refresh_token
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestParam String refreshToken) {
        String newTokenResponse = tokenService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(newTokenResponse);
    }

    // Método auxiliar para extraer el access_token del JSON
    private String extractAccessToken(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }

    // Método auxiliar para extraer el refresh_token del JSON
    private String extractRefreshToken(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonObject.get("refresh_token").getAsString();
    }

    // Método auxiliar para obtener el userId desde el endpoint /me de Spotify
    private String getSpotifyUserId(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.spotify.com/v1/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        // Extraer el campo "id" del cuerpo de la respuesta
        JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
        return jsonObject.get("id").getAsString();
    }
}
