package com.spotify.app.spotify_backend.controller;

import com.spotify.app.spotify_backend.dto.TopArtistsResponseDTO;
import com.spotify.app.spotify_backend.model.UserSession;
import com.spotify.app.spotify_backend.service.SpotifyApiClient;
import com.spotify.app.spotify_backend.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class SpotifyController {

    @Autowired
    private SpotifyApiClient spotifyApiClient;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/spotify/me/top/artists")
    public ResponseEntity<?> getTopArtists(HttpServletRequest request) {
        // Obtener el UUID desde las cookies
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(401).body("No session cookies found");
        }

        String uuid = Arrays.stream(cookies)
                .filter(cookie -> "sessionUUID".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (uuid == null) {
            return ResponseEntity.status(401).body("Session UUID is missing");
        }

        // Obtener la sesión desde el TokenService
        UserSession session = tokenService.getSession(uuid);
        if (session == null) {
            return ResponseEntity.status(401).body("Invalid session. Please login again.");
        }

        // Obtener el accessToken de la sesión
        String accessToken = session.getAccessToken();

        // Llamar al cliente de la API para obtener los artistas principales
        try {
            TopArtistsResponseDTO topArtists = spotifyApiClient.getTopArtists(accessToken);
            return ResponseEntity.ok(topArtists);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching top artists: " + e.getMessage());
        }
    }
}
