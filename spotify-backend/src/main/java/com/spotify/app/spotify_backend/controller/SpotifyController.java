package com.spotify.app.spotify_backend.controller;

import com.spotify.app.spotify_backend.dto.AlbumDTO;
import com.spotify.app.spotify_backend.dto.ArtistDTO;
import com.spotify.app.spotify_backend.dto.SearchResponseDTO;
import com.spotify.app.spotify_backend.dto.TopArtistsResponseDTO;
import com.spotify.app.spotify_backend.dto.TopTracksResponseDTO;
import com.spotify.app.spotify_backend.model.UserSession;
import com.spotify.app.spotify_backend.service.SpotifyApiClient;
import com.spotify.app.spotify_backend.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/api")
public class SpotifyController {

    @Autowired
    private SpotifyApiClient spotifyApiClient;

    @Autowired
    private SessionUtil sessionUtil;

    @GetMapping("/spotify/me/top/artists")
    public ResponseEntity<?> getTopArtists(HttpServletRequest request) {
        try {
            // Validar la sesión
            UserSession session = sessionUtil.validateSession(request);

            // Obtener el accessToken de la sesión
            String accessToken = session.getAccessToken();

            // Llamar al cliente de la API para obtener los artistas principales
            TopArtistsResponseDTO topArtists = spotifyApiClient.getTopArtists(accessToken);
            return ResponseEntity.ok(topArtists);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching top artists: " + e.getMessage());
        }
    }

    @GetMapping("/spotify/me/top/tracks")
    public ResponseEntity<?> getTopTracks(HttpServletRequest request) {
        try {
            // Validar la sesión
            UserSession session = sessionUtil.validateSession(request);

            // Obtener el accessToken de la sesión
            String accessToken = session.getAccessToken();

            // Llamar al cliente de la API para obtener los tracks principales
            TopTracksResponseDTO topTracks = spotifyApiClient.getTopTracks(accessToken);
            return ResponseEntity.ok(topTracks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching top tracks: " + e.getMessage());
        }
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable String id, HttpServletRequest request) {
        try {
            // Validar la sesión
            UserSession session = sessionUtil.validateSession(request);

            // Obtener el accessToken de la sesión
            String accessToken = session.getAccessToken();

            // Llamar al cliente de la API para obtener los datos del artista
            ArtistDTO artist = spotifyApiClient.getArtistById(id, accessToken);
            return ResponseEntity.ok(artist);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching artist data: " + e.getMessage());
        }
    }

    @GetMapping("/artists/{id}/top-tracks")
    public ResponseEntity<?> getArtistTopTracks(@PathVariable String id, HttpServletRequest request) {
        try {
            String accessToken = sessionUtil.validateSession(request).getAccessToken();
            TopTracksResponseDTO topTracks = spotifyApiClient.getArtistTopTracks(id, accessToken);
            return ResponseEntity.ok(topTracks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching top tracks: " + e.getMessage());
        }
    }

    @GetMapping("/artists/{id}/albums")
    public ResponseEntity<?> getArtistAlbums(@PathVariable String id, HttpServletRequest request) {
        try {
            String accessToken = sessionUtil.validateSession(request).getAccessToken();
            List<AlbumDTO> albums = spotifyApiClient.getArtistAlbums(id, accessToken);
            return ResponseEntity.ok(albums);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching albums: " + e.getMessage());
        }
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable String id, HttpServletRequest request) {
        try {
            // Validar la sesión
            UserSession session = sessionUtil.validateSession(request);

            // Obtener el accessToken de la sesión
            String accessToken = session.getAccessToken();

            // Llamar al cliente de la API para obtener los detalles del álbum
            AlbumDTO album = spotifyApiClient.getAlbumById(id, accessToken);
            return ResponseEntity.ok(album);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching album details: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam("q") String query,
            @RequestParam(value = "type", defaultValue = "artist,album,track") String type,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            HttpServletRequest request) {
        try {
            // Validar la sesión
            UserSession session = sessionUtil.validateSession(request);

            // Obtener el accessToken de la sesión
            String accessToken = session.getAccessToken();

            // Llamar al servicio para realizar la búsqueda
            SearchResponseDTO searchResults = spotifyApiClient.search(query, type, accessToken, limit, offset);

            return ResponseEntity.ok(searchResults);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error performing search: " + e.getMessage());
        }
    }

}
