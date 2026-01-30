package com.seplagseletivo.projeto_backend.controller;

import com.seplagseletivo.projeto_backend.model.Album;
import com.seplagseletivo.projeto_backend.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/albums")
@Tag(name = "Álbuns", description = "Endpoints para gerenciamento de álbuns")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    @Operation(summary = "Listar álbuns com paginação e filtro por tipo (Cantor/Banda)")

    public ResponseEntity<Page<Album>> listAll(
            @RequestParam(required = false) String artistType,
            Pageable pageable) {
        return ResponseEntity.ok(albumService.findAll(artistType, pageable));
    }

    @PostMapping
    public ResponseEntity<Album> create(@RequestBody Album album) {
        return ResponseEntity.ok(albumService.save(album));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> update(@PathVariable Long id, @RequestBody Album album) {
        return ResponseEntity.ok(albumService.update(id, album));
    }

    @PostMapping("/{id}/upload-cover")
    @Operation(summary = "Fazer upload da capa do álbum para o MinIO")
    public ResponseEntity<Album> uploadCover(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(albumService.uploadCover(id, file));
    }
}