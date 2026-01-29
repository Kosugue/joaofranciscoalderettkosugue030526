package com.seplagseletivo.projeto_backend.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://localhost:9000") // Porta padrão da API do MinIO
                .credentials("minioadmin", "minioadmin") // Usuário e senha padrão
                .build();
    }
}