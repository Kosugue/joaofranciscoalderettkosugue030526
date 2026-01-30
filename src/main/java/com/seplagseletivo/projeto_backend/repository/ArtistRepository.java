package com.seplagseletivo.projeto_backend.repository;

import com.seplagseletivo.projeto_backend.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    // Métodos para o Requisito (f)
    List<Artist> findAllByOrderByNameAsc();
    List<Artist> findAllByOrderByNameDesc();
}