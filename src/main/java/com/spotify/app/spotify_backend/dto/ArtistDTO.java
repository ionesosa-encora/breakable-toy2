package com.spotify.app.spotify_backend.dto;

import java.util.List;

public class ArtistDTO {
    private String id;
    private String name;
    private int popularity;
    private List<ImageDTO> images;
    private String uri;
    private FollowersDTO followers; // Información de seguidores del artista

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

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public FollowersDTO getFollowers() {
        return followers;
    }

    public void setFollowers(FollowersDTO followers) {
        this.followers = followers;
    }
}
