package com.spotify.app.spotify_backend.service;

import com.spotify.app.spotify_backend.dto.AlbumDTO;
import com.spotify.app.spotify_backend.dto.ArtistDTO;
import com.spotify.app.spotify_backend.dto.SearchResponseDTO;
import com.spotify.app.spotify_backend.dto.TopArtistsResponseDTO;
import com.spotify.app.spotify_backend.dto.TopTracksResponseDTO;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class SpotifyApiClient {

    @Value("${spotify.api.base-url}")
    private String spotifyApiBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

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

    public TopTracksResponseDTO getTopTracks(String accessToken) {
        String url = spotifyApiBaseUrl + "/me/top/tracks?limit=10";
    
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
    
        HttpEntity<Void> request = new HttpEntity<>(headers);
    
        ResponseEntity<TopTracksResponseDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            request,
            TopTracksResponseDTO.class
        );
    
        return response.getBody();
    }
    

    public ArtistDTO getArtistById(String artistId, String accessToken) {
        String url = spotifyApiBaseUrl + "/artists/" + artistId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<ArtistDTO> response = restTemplate.exchange(url, HttpMethod.GET, request, ArtistDTO.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching artist data from Spotify API: " + e.getMessage());
        }
    }

    public AlbumDTO getAlbumById(String albumId, String accessToken) {
        String url = spotifyApiBaseUrl + "/albums/" + albumId;

        // Configurar los encabezados de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // Realizar la solicitud a la API de Spotify
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AlbumDTO> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, request, AlbumDTO.class);

        // Devolver el álbum
        return response.getBody();
    }

    public SearchResponseDTO search(String query, String type, String accessToken, int limit, int offset) {
        // Construir la URL de búsqueda
        URI url = UriComponentsBuilder.fromHttpUrl(spotifyApiBaseUrl + "/search")
                .queryParam("q", query)
                .queryParam("type", type)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .build()
                .toUri();

        // Configurar los headers
        var headers = new org.springframework.http.HttpHeaders();
        headers.setBearerAuth(accessToken);

        // Crear la solicitud
        var entity = new org.springframework.http.HttpEntity<>(headers);

        // Realizar la solicitud a la API de Spotify
        var response = restTemplate.exchange(
            url,
            org.springframework.http.HttpMethod.GET,
            entity,
            SearchResponseDTO.class
        );

        return response.getBody();
    }


}
