package com.seplagseletivo.projeto_backend;

import com.seplagseletivo.projeto_backend.service.MinioService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MinioServiceTest {

    @Mock private MinioClient minioClient;
    @InjectMocks private MinioService minioService;

    @Test
    void shouldUploadFile() throws Exception {
        // ARRANJO
        MockMultipartFile file = new MockMultipartFile(
                "file", "teste.jpg", "image/jpeg", "conteudo".getBytes()
        );

        when(minioClient.bucketExists(any())).thenReturn(true);

        // AÇÃO
        minioService.uploadFile(file);

        // VERIFICAÇÃO (Requisito h)
        // Verifica se o método putObject foi chamado no cliente MinIO
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }
}