package com.spotify.app.spotify_backend.dto;

public class FollowersDTO {
    private String href;
    private int total;

    // Getters y Setters
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
