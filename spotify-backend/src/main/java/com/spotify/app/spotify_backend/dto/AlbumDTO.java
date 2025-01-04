package com.spotify.app.spotify_backend.dto;

import java.util.List;

/**
 * DTO para representar la información de un álbum.
 */
public class AlbumDTO {
    private String id;
    private String name;
    private List<ImageDTO> images; // Lista de imágenes asociadas al álbum
    private String releaseDate;   // Fecha de lanzamiento del álbum

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
}
