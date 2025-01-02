package com.spotify.app.spotify_backend.service;

import com.spotify.app.spotify_backend.dto.TopArtistsResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyApiClient {

    @Value("${spotify.api.base-url}")
    private String spotifyApiBaseUrl;

    // Obtener los artistas principales
    public TopArtistsResponseDTO getTopArtists(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = spotifyApiBaseUrl + "/me/top/artists?limit=10";

        // Configurar las cabeceras con el accessToken
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setBearerAuth(accessToken);

        // Hacer la solicitud GET a Spotify
        org.springframework.http.HttpEntity<Void> entity = new org.springframework.http.HttpEntity<>(headers);
        org.springframework.http.ResponseEntity<TopArtistsResponseDTO> response =
                restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, TopArtistsResponseDTO.class);

        // Retornar la respuesta deserializada en TopArtistsResponseDTO
        return response.getBody();
    }
}
