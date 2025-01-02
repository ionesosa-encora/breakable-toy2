package com.spotify.app.spotify_backend.service;

import com.spotify.app.spotify_backend.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    // Intercambiar el código por un TokenResponse
    public TokenResponse exchangeCodeForTokens(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://accounts.spotify.com/api/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=authorization_code&code=" + code + "&redirect_uri=" + redirectUri;

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        // Usar TokenResponse para mapear la respuesta JSON
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(url, request, TokenResponse.class);

        return response.getBody();
    }

    // Refrescar el accessToken
    public String refreshAccessToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://accounts.spotify.com/api/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=refresh_token&refresh_token=" + refreshToken;

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(url, request, TokenResponse.class);

        // Devolver solo el nuevo accessToken
        return response.getBody().getAccessToken();
    }

    // Validar si el accessToken ha expirado
    public boolean isTokenExpired(String accessToken) {
        // Implementación futura si hay un campo "exp" en el token
        return false; // Cambiar esta lógica si es necesario
    }
}
