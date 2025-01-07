package com.spotify.app.spotify_backend.service;

import com.spotify.app.spotify_backend.model.UserSession;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private final ConcurrentHashMap<String, UserSession> tokenStore = new ConcurrentHashMap<>();

    public void saveSession(String uuid, String accessToken, String refreshToken) {
        long currentTimeMillis = System.currentTimeMillis();
        UserSession session = new UserSession(accessToken, refreshToken, currentTimeMillis);
        tokenStore.put(uuid, session);
    }

    public UserSession getSession(String uuid) {
        return tokenStore.get(uuid);
    }

    public void updateSession(String uuid, UserSession updatedSession) {
        tokenStore.put(uuid, updatedSession);
    }
}
