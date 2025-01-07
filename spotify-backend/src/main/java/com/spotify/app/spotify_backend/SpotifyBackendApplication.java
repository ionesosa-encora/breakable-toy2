package com.spotify.app.spotify_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableCaching // Habilita el soporte para cache
@EnableRetry   // Habilita el soporte para retries
public class SpotifyBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpotifyBackendApplication.class, args);
    }
}
