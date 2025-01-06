package com.spotify.app.spotify_backend.service;

import com.spotify.app.spotify_backend.dto.AlbumDTO;
import com.spotify.app.spotify_backend.dto.ArtistDTO;
import com.spotify.app.spotify_backend.dto.PaginatedResultDTO;
import com.spotify.app.spotify_backend.dto.SearchResponseDTO;
import com.spotify.app.spotify_backend.dto.TopArtistsResponseDTO;
import com.spotify.app.spotify_backend.dto.TopTracksResponseDTO;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SpotifyApiClient {

    @Value("${spotify.api.base-url}")
    private String spotifyApiBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public TopArtistsResponseDTO getTopArtists(String accessToken) {
        String url = spotifyApiBaseUrl + "/me/top/artists?limit=10";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<TopArtistsResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, TopArtistsResponseDTO.class);

        return response.getBody();
    }

    public TopTracksResponseDTO getTopTracks(String accessToken) {
        String url = spotifyApiBaseUrl + "/me/top/tracks?limit=10";
    
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
    
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<TopTracksResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, TopTracksResponseDTO.class);
    
        return response.getBody(); // Devuelve el TopTracksResponseDTO completo
    }
    

    public ArtistDTO getArtistById(String artistId, String accessToken) {
        String url = spotifyApiBaseUrl + "/artists/" + artistId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<ArtistDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, ArtistDTO.class);

        return response.getBody();
    }

    public AlbumDTO getAlbumById(String albumId, String accessToken) {
        String url = spotifyApiBaseUrl + "/albums/" + albumId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<AlbumDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, AlbumDTO.class);

        return response.getBody();
    }

    public SearchResponseDTO search(String query, String type, String accessToken, int limit, int offset) {
        URI url = UriComponentsBuilder.fromHttpUrl(spotifyApiBaseUrl + "/search")
                .queryParam("q", query)
                .queryParam("type", type)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<SearchResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, SearchResponseDTO.class);

        return response.getBody();
    }

    // Nuevo método: Obtener los álbums de un artista
    public List<AlbumDTO> getArtistAlbums(String artistId, String accessToken) {
        URI url = UriComponentsBuilder.fromHttpUrl(spotifyApiBaseUrl + "/artists/" + artistId + "/albums")
                .queryParam("limit", 10)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<PaginatedResultDTO<AlbumDTO>> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<PaginatedResultDTO<AlbumDTO>>() {});

        return response.getBody().getItems();
    }

    public TopTracksResponseDTO getArtistTopTracks(String artistId, String accessToken) {
        URI url = UriComponentsBuilder.fromHttpUrl(spotifyApiBaseUrl + "/artists/" + artistId + "/top-tracks")
                .queryParam("market", "US") // Parametro requerido por la API de Spotify
                .build()
                .toUri();
    
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
    
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<TopTracksResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, TopTracksResponseDTO.class);
    
        return response.getBody();
    }
}
