package com.spotify.app.spotify_backend.service;

import com.spotify.app.spotify_backend.model.UserSession;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    private final ConcurrentHashMap<String, UserSession> tokenStore = new ConcurrentHashMap<>();

    // Guardar una nueva sesión usando el UUID
    public void saveSession(String uuid, String accessToken, String refreshToken) {
        tokenStore.put(uuid, new UserSession(accessToken, refreshToken));
        System.out.println("Session stored for UUID: " + uuid);
    }

    // Obtener la sesión por UUID
    public UserSession getSession(String uuid) {
        return tokenStore.get(uuid);
    }

    // Actualizar el accessToken en una sesión existente
    public void updateAccessToken(String uuid, String newAccessToken) {
        UserSession session = tokenStore.get(uuid);
        if (session != null) {
            session.setAccessToken(newAccessToken);
            System.out.println("Updated accessToken for UUID: " + uuid);
        }
    }
}
