package com.seplagseletivo.projeto_backend.service;

import com.seplagseletivo.projeto_backend.entity.Artist;
import com.seplagseletivo.projeto_backend.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    // Resolve o erro 'findAllSorted' (Requisito f)
    public List<Artist> findAllSorted(String direction) {
        if ("desc".equalsIgnoreCase(direction)) {
            return artistRepository.findAllByOrderByNameDesc();
        }
        return artistRepository.findAllByOrderByNameAsc();
    }

    // Resolve o erro 'save' (Requisito c)
    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    // Resolve o erro 'update' (Requisito c)
    public Artist update(Long id, Artist artistDetails) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista não encontrado"));
        artist.setName(artistDetails.getName());
        artist.setType(artistDetails.getType());
        return artistRepository.save(artist);
    }
}