package com.spotify.app.spotify_backend.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.spotify.app.spotify_backend.model.UserTokens;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    private final ConcurrentHashMap<String, UserTokens> tokenStore = new ConcurrentHashMap<>();

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    // Guardar tokens para un usuario específico
    public void saveTokens(String userId, String accessToken, String refreshToken) {
        System.out.println("Guardando tokens para el usuario: " + userId);
        System.out.println("Access Token: " + accessToken);
        System.out.println("Refresh Token: " + refreshToken);
        tokenStore.put(userId, new UserTokens(accessToken, refreshToken));
    }

    // Obtener los tokens de un usuario
    public UserTokens getTokens(String userId) {
        return tokenStore.get(userId);
    }

    // Actualizar solo el accessToken
    public void updateAccessToken(String userId, String newAccessToken) {
        UserTokens tokens = tokenStore.get(userId);
        if (tokens != null) {
            tokens.setAccessToken(newAccessToken);
        }
    }

    // Método para refrescar el access_token usando el refresh_token
    public String refreshAccessToken(String refreshToken) {
        String tokenUrl = "https://accounts.spotify.com/api/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        String body = "grant_type=refresh_token&refresh_token=" + refreshToken;

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        // Extraer el nuevo access_token de la respuesta
        String responseBody = response.getBody();
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }
}
