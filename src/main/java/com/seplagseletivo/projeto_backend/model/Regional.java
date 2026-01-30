package com.seplagseletivo.projeto_backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "regionais")
public class Regional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID interno do nosso banco

    @Column(name = "original_id", nullable = false)
    private Long originalId; // ID que veio da API externa

    private String nome;

    private boolean ativo;

    @Column(name = "data_sincronizacao")
    private LocalDateTime dataSincronizacao;

    // Construtor vazio obrigatório para JPA
    public Regional() {}

    // Construtor utilitário
    public Regional(Long originalId, String nome, boolean ativo) {
        this.originalId = originalId;
        this.nome = nome;
        this.ativo = ativo;
        this.dataSincronizacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOriginalId() { return originalId; }
    public void setOriginalId(Long originalId) { this.originalId = originalId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getDataSincronizacao() { return dataSincronizacao; }
    public void setDataSincronizacao(LocalDateTime dataSincronizacao) { this.dataSincronizacao = dataSincronizacao; }
}