package com.spotify.app.spotify_backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlbumDTO {
    private String id;
    private String name;
    private List<ImageDTO> images; // Lista de imágenes asociadas al álbum
    @JsonProperty("release_date") 
    private String releaseDate;   // Fecha de lanzamiento del álbum
    @JsonProperty("total_tracks") 
    private int totalTracks;      // Número total de canciones
    private List<ArtistDTO> artists; // Artistas asociados al álbum
    private TrackContainer tracks;  // Contenedor de las canciones

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }

    public List<ArtistDTO> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistDTO> artists) {
        this.artists = artists;
    }

    public TrackContainer getTracks() {
        return tracks;
    }

    public void setTracks(TrackContainer tracks) {
        this.tracks = tracks;
    }

    // Clase interna para el contenedor de tracks
    public static class TrackContainer {
        private List<TrackDTO> items;

        public List<TrackDTO> getItems() {
            return items;
        }

        public void setItems(List<TrackDTO> items) {
            this.items = items;
        }
    }
}
