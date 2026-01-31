package com.seplagseletivo.projeto_backend;

import com.seplagseletivo.projeto_backend.repository.ArtistRepository;
import com.seplagseletivo.projeto_backend.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    // CORREÇÃO: Alterado de ArtistServiceTest para ArtistService
    @InjectMocks
    private ArtistService artistService;

    @Test
    void shouldSortAscendingByDefault() {
        artistService.findAllSorted("asc");
        verify(artistRepository).findAllByOrderByNameAsc();
    }

    @Test
    void shouldSortDescendingWhenRequested() {
        artistService.findAllSorted("desc");
        verify(artistRepository).findAllByOrderByNameDesc();
    }
}