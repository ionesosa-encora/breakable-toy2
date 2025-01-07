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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SpotifyApiClient {

    @Value("${spotify.api.base-url}")
    private String spotifyApiBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Retryable(
        value = {HttpClientErrorException.TooManyRequests.class, HttpServerErrorException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @Cacheable(value = "topArtists", key = "#accessToken")
    public TopArtistsResponseDTO getTopArtists(String accessToken) {
        String url = spotifyApiBaseUrl + "/me/top/artists?limit=10";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<TopArtistsResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, TopArtistsResponseDTO.class);

        return response.getBody();
    }

    @Retryable(
        value = {HttpClientErrorException.TooManyRequests.class, HttpServerErrorException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @Cacheable(value = "topTracks", key = "#accessToken")
    public TopTracksResponseDTO getTopTracks(String accessToken) {
        String url = spotifyApiBaseUrl + "/me/top/tracks?limit=10";
    
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
    
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<TopTracksResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, TopTracksResponseDTO.class);
    
        return response.getBody();
    }

    @Retryable(
        value = {HttpClientErrorException.TooManyRequests.class, HttpServerErrorException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @Cacheable(value = "artistById", key = "#artistId")
    public ArtistDTO getArtistById(String artistId, String accessToken) {
        String url = spotifyApiBaseUrl + "/artists/" + artistId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<ArtistDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, ArtistDTO.class);

        return response.getBody();
    }

    @Retryable(
        value = {HttpClientErrorException.TooManyRequests.class, HttpServerErrorException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @Cacheable(value = "albumById", key = "#albumId")
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

    @Retryable(
        value = {HttpClientErrorException.TooManyRequests.class, HttpServerErrorException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @Cacheable(value = "artistAlbums", key = "#artistId")
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

    @Retryable(
        value = {HttpClientErrorException.TooManyRequests.class, HttpServerErrorException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @Cacheable(value = "artistTopTracks", key = "#artistId")
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
