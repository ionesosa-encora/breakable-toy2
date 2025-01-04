package com.spotify.app.spotify_backend.dto;

/**
 * DTO para encapsular la respuesta de búsqueda de la API de Spotify.
 * Contiene las secciones de artistas, álbumes y canciones con información de paginación.
 */
public class SearchResponseDTO {
    private PaginatedResultDTO<ArtistDTO> artists; // Encapsula la lista de artistas con paginación
    private PaginatedResultDTO<AlbumDTO> albums;  // Encapsula la lista de álbumes con paginación
    private PaginatedResultDTO<TrackDTO> tracks;  // Encapsula la lista de canciones con paginación

    // Getters y Setters
    public PaginatedResultDTO<ArtistDTO> getArtists() {
        return artists;
    }

    public void setArtists(PaginatedResultDTO<ArtistDTO> artists) {
        this.artists = artists;
    }

    public PaginatedResultDTO<AlbumDTO> getAlbums() {
        return albums;
    }

    public void setAlbums(PaginatedResultDTO<AlbumDTO> albums) {
        this.albums = albums;
    }

    public PaginatedResultDTO<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(PaginatedResultDTO<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}
