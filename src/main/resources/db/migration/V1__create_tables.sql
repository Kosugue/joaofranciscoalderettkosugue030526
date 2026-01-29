CREATE TABLE artists (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
    -- Requisito (e): O campo 'type' define se é "Cantor" ou "Banda"
                         type VARCHAR(50) NOT NULL
);

-- Requisito (f): Índice para busca e ordenação rápida por nome
CREATE INDEX idx_artist_name ON artists(name);

CREATE TABLE albums (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
    -- Requisito (g/h): Aqui guardaremos o nome do arquivo gerado no MinIO
                        cover_image_key VARCHAR(255),
                        artist_id BIGINT NOT NULL,
                        CONSTRAINT fk_artist FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE CASCADE
);