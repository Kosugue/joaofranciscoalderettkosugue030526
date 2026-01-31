package com.seplagseletivo.projeto_backend;

import com.seplagseletivo.projeto_backend.model.Album;
import com.seplagseletivo.projeto_backend.model.Artist;
import com.seplagseletivo.projeto_backend.repository.AlbumRepository;
import com.seplagseletivo.projeto_backend.service.AlbumService;
import com.seplagseletivo.projeto_backend.service.MinioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private MinioService minioService;

    @Mock
    private SimpMessageSendingOperations messagingTemplate;


    @InjectMocks
    private AlbumService albumService;

    @Test
    void shouldFindAllWithPaginationAndPresignedUrl() {
        // ARRANJO
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("Guns");
        artist.setType("Banda");

        Album album = new Album();
        album.setId(1L);
        album.setTitle("Use Your Illusion");
        album.setCoverImageKey("cover.jpg");
        album.setArtist(artist);

        Page<Album> page = new PageImpl<>(List.of(album));
        Pageable pageable = PageRequest.of(0, 10);

        // Configura o mock do repositório
        when(albumRepository.findAll(pageable)).thenReturn(page);

        // Mock do Link Pré-assinado (MinIO)
        when(minioService.getPresignedUrl("cover.jpg"))
                .thenReturn("http://minio/signed-url?expiry=...");

        // AÇÃO

        Page<Album> result = albumService.findAll(null, pageable);

        // VERIFICAÇÃO
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("http://minio/signed-url?expiry=...", result.getContent().get(0).getCoverImageUrl());

        verify(albumRepository).findAll(pageable);
    }

    @Test
    void shouldFilterByArtistType() {
        // ARRANJO
        Pageable pageable = PageRequest.of(0, 10);
        when(albumRepository.findByArtist_TypeIgnoreCase(eq("Banda"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // AÇÃO
        albumService.findAll("Banda", pageable);

        // VERIFICAÇÃO
        verify(albumRepository).findByArtist_TypeIgnoreCase(eq("Banda"), any(Pageable.class));
    }
}