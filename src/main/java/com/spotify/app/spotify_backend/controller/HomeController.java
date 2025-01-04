package com.spotify.app.spotify_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> home() {
        String message = """
                {
                    "Spotify App",
                    "login_url": "/auth/spotify/login"
                }
                """;

        return ResponseEntity.ok(message);
    }
}
