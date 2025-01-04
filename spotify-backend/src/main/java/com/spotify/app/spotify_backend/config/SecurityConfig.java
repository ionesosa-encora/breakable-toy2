package com.spotify.app.spotify_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF solo para pruebas locales
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/spotify/**").permitAll()
                .requestMatchers("/spotify/me/top/artists").permitAll()
                .anyRequest().permitAll()
            );

        return http.build();
    }
}
