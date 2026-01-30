package com.seplagseletivo.projeto_backend.controller;

import com.seplagseletivo.projeto_backend.model.Regional;
import com.seplagseletivo.projeto_backend.service.RegionalSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regionais")
@Tag(name = "Regionais", description = "Sincronização com API externa")
public class RegionalController {

    private final RegionalSyncService service;

    public RegionalController(RegionalSyncService service) {
        this.service = service;
    }

    @PostMapping("/sync")
    @Operation(summary = "Forçar sincronização com a API externa")
    public ResponseEntity<String> sync() {
        return ResponseEntity.ok(service.syncRegionais());
    }

    @GetMapping
    @Operation(summary = "Listar todas as regionais (histórico completo)")
    public ResponseEntity<List<Regional>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
}