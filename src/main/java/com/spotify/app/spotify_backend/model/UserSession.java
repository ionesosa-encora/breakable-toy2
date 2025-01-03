package com.spotify.app.spotify_backend.model;

/**
 * Modelo para representar una sesión activa de un usuario.
 */
public class UserSession {
    private String accessToken;       // Token de acceso para la API de Spotify
    private String refreshToken;      // Token para refrescar el accessToken
    private long tokenAcquiredTime;   // Marca de tiempo en milisegundos cuando se obtuvo el token

    // Constructor
    public UserSession(String accessToken, String refreshToken, long tokenAcquiredTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenAcquiredTime = tokenAcquiredTime;
    }

    // Getters y Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getTokenAcquiredTime() {
        return tokenAcquiredTime;
    }

    public void setTokenAcquiredTime(long tokenAcquiredTime) {
        this.tokenAcquiredTime = tokenAcquiredTime;
    }
}
