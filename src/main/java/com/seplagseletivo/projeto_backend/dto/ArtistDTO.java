package com.seplagseletivo.projeto_backend.dto;

import java.util.List;

public class ArtistDTO {

    private Long id;
    private String name;
    // Requisito (e): Define se é Cantor ou Banda
    private String type;
    private List<AlbumDTO> albums;

    // Construtor padrão (Vazio)
    public ArtistDTO() {
    }

    // Construtor completo
    public ArtistDTO(Long id, String name, String type, List<AlbumDTO> albums) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.albums = albums;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AlbumDTO> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumDTO> albums) {
        this.albums = albums;
    }
}