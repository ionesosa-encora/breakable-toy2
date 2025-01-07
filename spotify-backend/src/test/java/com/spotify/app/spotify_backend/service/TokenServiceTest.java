package com.spotify.app.spotify_backend.service;

import com.spotify.app.spotify_backend.model.UserSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenServiceTest {

    private final TokenService tokenService = new TokenService();

    @Test
    public void testSaveSession() {
        String uuid = "mockUUID";
        tokenService.saveSession(uuid, "mockAccessToken", "mockRefreshToken");

        UserSession session = tokenService.getSession(uuid);
        assertNotNull(session);
        assertEquals("mockAccessToken", session.getAccessToken());
    }

    @Test
    public void testGetSession() {
        String uuid = "mockUUID";
        tokenService.saveSession(uuid, "mockAccessToken", "mockRefreshToken");

        UserSession session = tokenService.getSession(uuid);
        assertNotNull(session);
        assertEquals("mockAccessToken", session.getAccessToken());
    }

    @Test
    public void testUpdateSession() {
        String uuid = "mockUUID";
        tokenService.saveSession(uuid, "mockAccessToken", "mockRefreshToken");

        UserSession updatedSession = new UserSession("newAccessToken", "newRefreshToken", System.currentTimeMillis());
        tokenService.updateSession(uuid, updatedSession);

        UserSession session = tokenService.getSession(uuid);
        assertEquals("newAccessToken", session.getAccessToken());
    }
}
