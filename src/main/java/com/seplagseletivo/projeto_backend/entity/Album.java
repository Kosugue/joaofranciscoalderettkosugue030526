package com.seplagseletivo.projeto_backend.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "albums")
public class Album {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;


    private String coverImageKey;


    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;


    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getCoverImageKey() {
        return coverImageKey;
    }


    public Artist getArtist() {
        return artist;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setCoverImageKey(String coverImageKey) {
        this.coverImageKey = coverImageKey;
    }


    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
