package com.spotify.app.spotify_backend.controller;

import com.spotify.app.spotify_backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/spotify")
public class SpotifyController {

    @Autowired
    private TokenService tokenService;

    // Endpoint para obtener los 10 artistas principales
    @GetMapping("/me/top/artists")
    public ResponseEntity<String> getTopArtists(@RequestHeader("user-id") String userId) {
        // Obtener el accessToken del TokenService
        String accessToken = tokenService.getTokens(userId).getAccessToken();

        // Verificar si el token existe
        if (accessToken == null) {
            return ResponseEntity.status(401).body("Access token no encontrado para el usuario: " + userId);
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.spotify.com/v1/me/top/artists?limit=10"; // Limitar a 10 artistas

        // Configurar las cabeceras con el token de acceso
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        // Hacer la solicitud a Spotify
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        // Devolver la respuesta de Spotify
        return ResponseEntity.ok(response.getBody());
    }
}
