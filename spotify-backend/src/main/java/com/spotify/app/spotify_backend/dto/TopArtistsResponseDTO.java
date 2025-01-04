package com.spotify.app.spotify_backend.dto;

import java.util.List;

public class TopArtistsResponseDTO {
    private List<ArtistDTO> items;

    // Getters y Setters
    public List<ArtistDTO> getItems() {
        return items;
    }

    public void setItems(List<ArtistDTO> items) {
        this.items = items;
    }
}
