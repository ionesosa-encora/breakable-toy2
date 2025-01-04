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
        
        // Permitir acceso sin restricciones a las rutas de login y callback
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/") || requestURI.startsWith("/auth/spotify/login") || requestURI.startsWith("/auth/spotify/callback")) {
            filterChain.doFilter(request, response);
            return;
        }
        
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
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Usamos SC_UNAUTHORIZED directamente
            response.getWriter().write("Unauthorized: No session UUID found");
            return;
        }

        // Obtener la sesión desde el TokenService
        UserSession session = tokenService.getSession(uuid);

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Usamos SC_UNAUTHORIZED directamente
            response.getWriter().write("Unauthorized: Invalid session");
            return;
        }

        // Validar si el accessToken ha expirado
        if (authService.isTokenExpired(session)) {
            try {
                String newAccessToken = authService.refreshAccessToken(session.getRefreshToken());
                session.setAccessToken(newAccessToken);
                session.setTokenAcquiredTime(System.currentTimeMillis());
                tokenService.updateSession(uuid, session);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Usamos SC_UNAUTHORIZED directamente
                response.getWriter().write("Unauthorized: Unable to refresh access token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
