package com.spotify.app.spotify_backend.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

public class TopTracksResponseDTO {
    @JsonAlias({"items", "tracks"}) // Mapea tanto "items" como "tracks" al mismo atributo
    private List<TrackDTO> tracks;  // Lista de pistas principales

    private int total;
    private int limit;
    private int offset;
    private String href;
    private String next;
    private String previous;

    // Getters y Setters
    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
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

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
