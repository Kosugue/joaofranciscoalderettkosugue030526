package com.seplagseletivo.projeto_backend.repository;

import com.seplagseletivo.projeto_backend.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    // Requisito (e): Consulta parametrizada para filtrar por Cantor ou Banda
    @Query("SELECT a FROM Album a WHERE a.artist.type = :type")
    Page<Album> findByArtist_TypeIgnoreCase(String type, Pageable pageable);
}