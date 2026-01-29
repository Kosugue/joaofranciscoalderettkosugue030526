package com.seplagseletivo.projeto_backend.dto;

public class AlbumDTO {

    private Long id;
    private String title;

    // Requisito (e): Nome do artista e tipo (Cantor ou Banda)
    private String artistName;
    private String artistType;

    // Requisito (i): URL pré-assinada do MinIO (expira em 30 min)
    private String coverImageUrl;

    // Construtor padrão (Vazio)
    public AlbumDTO() {
    }

    // Construtor completo
    public AlbumDTO(Long id, String title, String artistName, String artistType, String coverImageUrl) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.artistType = artistType;
        this.coverImageUrl = coverImageUrl;
    }

    // Getters e Setters (Essenciais para o Spring converter para JSON)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getArtistType() { return artistType; }
    public void setArtistType(String artistType) { this.artistType = artistType; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
}