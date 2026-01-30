package com.seplagseletivo.projeto_backend.repository;

import com.seplagseletivo.projeto_backend.model.Regional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionalRepository extends JpaRepository<Regional, Long> {
    // Busca apenas as regionais que estão ativas no momento
    List<Regional> findByAtivoTrue();

    // Busca regional ativa por ID original (da API)
    Optional<Regional> findByOriginalIdAndAtivoTrue(Long originalId);
}