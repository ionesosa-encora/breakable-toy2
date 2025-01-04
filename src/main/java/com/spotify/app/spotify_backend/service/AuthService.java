package com.spotify.app.spotify_backend.service;

import com.spotify.app.spotify_backend.dto.TokenResponse;
import com.spotify.app.spotify_backend.model.UserSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
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
        String url = "https://accounts.spotify.com/api/token";
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);
    
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);
    
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    
        try {
            // Inicializar RestTemplate localmente
            RestTemplate restTemplate = new RestTemplate();
    
            ResponseEntity<TokenResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                TokenResponse.class
            );
    
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid authorization code or code expired. Please try logging in again.");
            } else {
                throw new RuntimeException("Error during token exchange: " + e.getMessage());
            }
        }
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
    public boolean isTokenExpired(UserSession session) {
        long currentTimeMillis = System.currentTimeMillis();
        long tokenAgeMillis = currentTimeMillis - session.getTokenAcquiredTime();
        long tokenAgeSeconds = tokenAgeMillis / 1000;
        return tokenAgeSeconds >= 3600; // 3600 segundos = 1 hora
    }
}
