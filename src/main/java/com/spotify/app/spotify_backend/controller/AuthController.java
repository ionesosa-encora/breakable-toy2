package com.spotify.app.spotify_backend.controller;

import com.spotify.app.spotify_backend.dto.TokenResponse;
import com.spotify.app.spotify_backend.service.AuthService;
import com.spotify.app.spotify_backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/auth/spotify")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        String authorizationUrl = "https://accounts.spotify.com/authorize?" +
                "client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri +
                "&scope=user-top-read";
        return ResponseEntity.status(302).location(URI.create(authorizationUrl)).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<String> callback(@RequestParam String code) {
        // Obtener los tokens usando el DTO
        TokenResponse tokenResponse = authService.exchangeCodeForTokens(code);

        // Generar un UUID único para esta sesión
        String uuid = UUID.randomUUID().toString();

        // Guardar la sesión en TokenService
        tokenService.saveSession(uuid, tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());

        // Crear la cookie con el UUID
        ResponseCookie uuidCookie = ResponseCookie.from("sessionUUID", uuid)
                .httpOnly(true)
                .secure(false) // Cambiar a true en producción
                .path("/")
                .maxAge(3600) // 1 hora
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, uuidCookie.toString())
                .body("Session created successfully");
    }
}
