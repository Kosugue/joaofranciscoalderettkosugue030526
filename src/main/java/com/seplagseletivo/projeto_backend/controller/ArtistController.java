package com.seplagseletivo.projeto_backend.controller;

import com.seplagseletivo.projeto_backend.entity.Artist;
import com.seplagseletivo.projeto_backend.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artists") // Requisito (j): Versionamento
@Tag(name = "Artistas", description = "Endpoints para gerenciamentos de artistas")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping // Requisito (c) e (f): GET com ordenação
    @Operation(summary = "Listar artistas com ordenação alfabética (asc/desc)")
    public ResponseEntity<List<Artist>> listAll(
            @RequestParam(defaultValue = "asc") String sort) {
        return ResponseEntity.ok(artistService.findAllSorted(sort));
    }

    @PostMapping // Requisito (c): Criar artista
    public ResponseEntity<Artist> create(@RequestBody Artist artist) {
        return ResponseEntity.ok(artistService.save(artist));
    }

    @PutMapping("/{id}") // Requisito (c): Atualizar artista
    public ResponseEntity<Artist> update(@PathVariable Long id, @RequestBody Artist artist) {
        return ResponseEntity.ok(artistService.update(id, artist));
    }
}