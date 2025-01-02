package com.spotify.app.spotify_backend.config;

import com.spotify.app.spotify_backend.model.UserSession;
import com.spotify.app.spotify_backend.service.AuthService;
import com.spotify.app.spotify_backend.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthService authService;

    public AuthTokenFilter(TokenService tokenService, AuthService authService) {
        this.tokenService = tokenService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtener el UUID desde las cookies
        String uuid = null;
        if (request.getCookies() != null) {
            uuid = Arrays.stream(request.getCookies())
                    .filter(cookie -> "sessionUUID".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if (uuid == null) {
            System.out.println("No UUID found in cookies for request: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener la sesión desde el TokenService
        UserSession session = tokenService.getSession(uuid);

        if (session == null) {
            System.out.println("No session found for UUID: " + uuid);
            filterChain.doFilter(request, response);
            return;
        }

        // Validar si el accessToken ha expirado
        if (authService.isTokenExpired(session.getAccessToken())) {
            System.out.println("Access token expired for UUID: " + uuid);
            String newAccessToken = authService.refreshAccessToken(session.getRefreshToken());
            tokenService.updateAccessToken(uuid, newAccessToken);
        }

        filterChain.doFilter(request, response);
    }
}
