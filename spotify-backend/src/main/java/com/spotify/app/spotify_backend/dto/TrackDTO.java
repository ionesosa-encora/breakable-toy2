package com.spotify.app.spotify_backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrackDTO {
    private String id;
    private String name;
    private List<ArtistDTO> artists; // Lista de artistas asociados a la pista
    private AlbumDTO album;          // Información del álbum al que pertenece la pista
    private int popularity;          // Popularidad de la pista (0-100)
    @JsonProperty("duration_ms") 
    private int durationMs;          // Duración de la pista en milisegundos
    private String previewUrl;       // URL para una vista previa de 30 segundos de la pista

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

    public List<ArtistDTO> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistDTO> artists) {
        this.artists = artists;
    }

    public AlbumDTO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDTO album) {
        this.album = album;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
