-- Inserindo os Artistas (Requisito E: Identificando se é Cantor ou Banda)
INSERT INTO artists (name, type) VALUES ('Serj Tankian', 'Cantor');
INSERT INTO artists (name, type) VALUES ('Mike Shinoda', 'Cantor');
INSERT INTO artists (name, type) VALUES ('Michel Teló', 'Cantor');
INSERT INTO artists (name, type) VALUES ('Guns N’ Roses', 'Banda');

-- Álbuns do Serj Tankian (ID 1)
INSERT INTO albums (title, artist_id) VALUES ('Harakiri', 1);
INSERT INTO albums (title, artist_id) VALUES ('Black Blooms', 1);
INSERT INTO albums (title, artist_id) VALUES ('The Rough Dog', 1);

-- Álbuns do Mike Shinoda (ID 2)
INSERT INTO albums (title, artist_id) VALUES ('The Rising Tied', 2);
INSERT INTO albums (title, artist_id) VALUES ('Post Traumatic', 2);
INSERT INTO albums (title, artist_id) VALUES ('Post Traumatic EP', 2);
INSERT INTO albums (title, artist_id) VALUES ('Where’d You Go', 2);

-- Álbuns do Michel Teló (ID 3)
INSERT INTO albums (title, artist_id) VALUES ('Bem Sertanejo', 3);
INSERT INTO albums (title, artist_id) VALUES ('Bem Sertanejo - O Show (Ao Vivo)', 3);
INSERT INTO albums (title, artist_id) VALUES ('Bem Sertanejo - (1ª Temporada) - EP', 3);

-- Álbuns do Guns N’ Roses (ID 4)
INSERT INTO albums (title, artist_id) VALUES ('Use Your Illusion I', 4);
INSERT INTO albums (title, artist_id) VALUES ('Use Your Illusion II', 4);
INSERT INTO albums (title, artist_id) VALUES ('Greatest Hits', 4);