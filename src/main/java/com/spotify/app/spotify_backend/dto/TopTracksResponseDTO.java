package com.spotify.app.spotify_backend.dto;

import java.util.List;

public class TopTracksResponseDTO {
    private List<TrackDTO> items; // Lista de pistas principales
    private int total;            // Número total de pistas
    private int limit;            // Límite por página
    private int offset;           // Desplazamiento actual
    private String href;          // URL de la solicitud actual
    private String next;          // URL para la próxima página
    private String previous;      // URL para la página anterior

    // Getters y Setters
    public List<TrackDTO> getItems() {
        return items;
    }

    public void setItems(List<TrackDTO> items) {
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
