package com.spotify.app.spotify_backend.dto;

import java.util.List;

public class ArtistDTO {
    private ExternalUrlsDTO external_urls;
    private FollowersDTO followers;
    private List<String> genres;
    private String href;
    private String id;
    private List<ImageDTO> images;
    private String name;
    private int popularity;
    private String type;
    private String uri;

    // Getters y Setters
    public ExternalUrlsDTO getExternal_urls() {
        return external_urls;
    }

    public void setExternal_urls(ExternalUrlsDTO external_urls) {
        this.external_urls = external_urls;
    }

    public FollowersDTO getFollowers() {
        return followers;
    }

    public void setFollowers(FollowersDTO followers) {
        this.followers = followers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
