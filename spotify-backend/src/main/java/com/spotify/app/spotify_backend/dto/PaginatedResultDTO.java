package com.spotify.app.spotify_backend.dto;

import java.util.List;

/**
 * DTO genérico para encapsular resultados paginados de la API de Spotify.
 * @param <T> Tipo de los ítems en la lista (por ejemplo, ArtistDTO, AlbumDTO, TrackDTO)
 */
public class PaginatedResultDTO<T> {
    private String href;           // URL de la página actual
    private List<T> items;         // Lista de ítems (artistas, álbumes, canciones, etc.)
    private int limit;             // Número máximo de ítems por página
    private int offset;            // Índice inicial de los ítems
    private int total;             // Número total de ítems disponibles
    private String next;           // URL de la siguiente página (puede ser null)
    private String previous;       // URL de la página anterior (puede ser null)

    // Getters y Setters
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
