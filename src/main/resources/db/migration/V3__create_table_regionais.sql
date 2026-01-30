CREATE TABLE regionais (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           original_id BIGINT NOT NULL,
                           nome VARCHAR(200),
                           ativo BOOLEAN NOT NULL,
                           data_sincronizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);