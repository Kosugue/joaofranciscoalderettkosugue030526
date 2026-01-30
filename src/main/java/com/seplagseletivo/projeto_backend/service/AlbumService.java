package com.seplagseletivo.projeto_backend.service;

import com.seplagseletivo.projeto_backend.entity.Album;
import com.seplagseletivo.projeto_backend.repository.AlbumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final MinioService minioService;
    private final SimpMessageSendingOperations messagingTemplate;

    // Construtor com injeção de dependências
    public AlbumService(AlbumRepository albumRepository,
                        MinioService minioService,
                        SimpMessageSendingOperations messagingTemplate) {
        this.albumRepository = albumRepository;
        this.minioService = minioService;
        this.messagingTemplate = messagingTemplate;
    }

    // Atende Requisito (d), (e) e (i) - Listagem com URL pré-assinada
    public Page<Album> findAll(String artistType, Pageable pageable) {
        Page<Album> page;

        if (artistType != null) {
            // OBS: O método no Repository deve ser findByArtist_TypeIgnoreCase
            page = albumRepository.findByArtist_TypeIgnoreCase(artistType, pageable);
        } else {
            page = albumRepository.findAll(pageable);
        }

        // Percorre a lista e gera o link para quem tem capa (Requisito i)
        page.forEach(album -> {
            if (album.getCoverImageKey() != null && !album.getCoverImageKey().isEmpty()) {
                String url = minioService.getPresignedUrl(album.getCoverImageKey());
                album.setCoverImageUrl(url);
            }
        });

        return page;
    }

    // Atende Requisito (c) - Salvar e Notificar
    public Album save(Album album) {
        Album savedAlbum = albumRepository.save(album);

        // Envia notificação WebSocket
        messagingTemplate.convertAndSend("/topic/albums", "Novo álbum cadastrado: " + savedAlbum.getTitle());

        return savedAlbum;
    }

    // Atende Requisito (c) - Atualizar
    public Album update(Long id, Album album) {
        Album existing = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado"));

        existing.setTitle(album.getTitle());

        return albumRepository.save(existing);
    }

    // Atende Requisito (g, h) - Upload
    public Album uploadCover(Long id, MultipartFile file) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado"));

        String key = minioService.uploadFile(file);

        // Atualiza a chave da imagem na entidade
        album.setCoverImageKey(key);

        return albumRepository.save(album);
    }
}