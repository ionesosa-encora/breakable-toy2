package com.spotify.app.spotify_backend.utils;

import com.spotify.app.spotify_backend.model.UserSession;
import com.spotify.app.spotify_backend.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SessionUtil {

    private final TokenService tokenService;

    public SessionUtil(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public UserSession validateSession(HttpServletRequest request) {
        System.out.println("Validating session...");
    
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("No session cookies found.");
            throw new RuntimeException("No session cookies found");
        }
    
        String uuid = Arrays.stream(cookies)
                .filter(cookie -> "sessionUUID".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    
        if (uuid == null) {
            System.out.println("Session UUID is missing.");
            throw new RuntimeException("Session UUID is missing");
        }
    
        UserSession session = tokenService.getSession(uuid);
        if (session == null) {
            System.out.println("Invalid session. UUID: " + uuid);
            throw new RuntimeException("Invalid session. Please login again.");
        }
    
        System.out.println("Session validated successfully for UUID: " + uuid);
        return session;
    }
}
