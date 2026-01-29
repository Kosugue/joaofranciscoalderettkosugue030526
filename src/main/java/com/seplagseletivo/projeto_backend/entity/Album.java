package com.seplagseletivo.projeto_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // Armazena a chave/nome do arquivo no MinIO (Persistido no banco)
    private String coverImageKey;

    @Transient // Não é persistido no banco de dados
    private String coverImageUrl;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    // Construtores
    public Album() {}

    public Album(String title, Artist artist) {
        this.title = title;
        this.artist = artist;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }

    public String getCoverImageKey() {
        return coverImageKey;
    }

    public void setCoverImageKey(String coverImageKey) {
        this.coverImageKey = coverImageKey;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }
}