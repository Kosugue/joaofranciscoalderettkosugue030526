package com.seplagseletivo.projeto_backend.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String BUCKET_NAME = "music-covers";

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFile(MultipartFile file) {
        try {
            boolean found = minioClient.bucketExists(io.minio.BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (!found) {
                minioClient.makeBucket(io.minio.MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Falha no upload para o MinIO: " + e.getMessage());
        }
    }

    /**
     * Gera uma URL pré-assinada válida por 30 minutos (Requisito i).
     */
    public String getPresignedUrl(String objectName) {
        if (objectName == null || objectName.isEmpty()) return null;

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(BUCKET_NAME)
                            .object(objectName)
                            .expiry(30, TimeUnit.MINUTES)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar URL assinada: " + e.getMessage());
        }
    }
}