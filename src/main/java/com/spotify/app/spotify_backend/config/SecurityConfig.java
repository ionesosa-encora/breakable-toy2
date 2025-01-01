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
            .csrf().disable() // Deshabilitar CSRF para facilitar pruebas
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso público a los endpoints de autenticación
                .requestMatchers("/auth/spotify/**").permitAll()
                // Permitir acceso al endpoint de top artists
                .requestMatchers("/spotify/me/top/artists").permitAll()
                // Bloquear todos los demás endpoints
                .anyRequest().authenticated()
            )
            .formLogin().disable(); // Deshabilitar el inicio de sesión por defecto

        return http.build();
    }
}
