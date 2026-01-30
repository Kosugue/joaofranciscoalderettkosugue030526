package com.seplagseletivo.projeto_backend.service;

import com.seplagseletivo.projeto_backend.dto.RegionalDTO;
import com.seplagseletivo.projeto_backend.entity.Regional;
import com.seplagseletivo.projeto_backend.repository.RegionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RegionalSyncService {

    private final RegionalRepository repository;
    private final RestTemplate restTemplate;

    public RegionalSyncService(RegionalRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public List<Regional> listAll() {
        return repository.findAll();
    }

    @Transactional
    public String syncRegionais() {
        String url = "https://integrador-argus-api.geia.vip/v1/regionais";

        // 1. Buscar dados da API Externa
        RegionalDTO[] response = restTemplate.getForObject(url, RegionalDTO[].class);
        if (response == null) return "Erro ao buscar dados da API.";

        List<RegionalDTO> apiList = Arrays.asList(response);

        // 2. Buscar dados atuais do Banco (apenas ativos)
        // Mapa: Original_ID -> Entidade Regional
        Map<Long, Regional> dbMap = repository.findByAtivoTrue().stream()
                .collect(Collectors.toMap(Regional::getOriginalId, Function.identity()));

        int criados = 0;
        int atualizados = 0;
        int inativados = 0;

        Set<Long> processadosIds = new HashSet<>();

        // 3. Processar API -> Banco
        for (RegionalDTO dto : apiList) {
            processadosIds.add(dto.getId());

            if (dbMap.containsKey(dto.getId())) {
                // Já existe no banco. Verificar se mudou o nome.
                Regional existente = dbMap.get(dto.getId());

                if (!existente.getNome().equals(dto.getNome())) {
                    // CASO 3: Atributo alterado -> Inativar antigo e criar novo
                    existente.setAtivo(false);
                    repository.save(existente);

                    Regional novo = new Regional(dto.getId(), dto.getNome(), true);
                    repository.save(novo);
                    atualizados++;
                }
                // Se nome for igual, não faz nada (mantém ativo)
            } else {
                // CASO 1: Novo no endpoint -> Inserir
                Regional novo = new Regional(dto.getId(), dto.getNome(), true);
                repository.save(novo);
                criados++;
            }
        }

        // 4. Processar Banco -> API (O que sumiu da API deve ser inativado)
        for (Map.Entry<Long, Regional> entry : dbMap.entrySet()) {
            if (!processadosIds.contains(entry.getKey())) {
                // CASO 2: Ausente no endpoint -> Inativar
                Regional paraInativar = entry.getValue();
                paraInativar.setAtivo(false);
                repository.save(paraInativar);
                inativados++;
            }
        }

        return String.format("Sincronização concluída. Criados: %d, Atualizados (Versionados): %d, Inativados: %d",
                criados, atualizados, inativados);
    }
}