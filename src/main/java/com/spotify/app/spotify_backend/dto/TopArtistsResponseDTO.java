package com.spotify.app.spotify_backend.dto;

import java.util.List;

public class TopArtistsResponseDTO {
    private List<ArtistDTO> items;
    private int total;
    private int limit;
    private int offset;
    private String previous;
    private String href;
    private String next;

    // Getters y Setters
    public List<ArtistDTO> getItems() {
        return items;
    }

    public void setItems(List<ArtistDTO> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
