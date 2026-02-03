# API de Gestão Musical - Seletivo Seplag

**Desenvolvido por:** João Francisco Alderett Kosugue

**Cargo Pretendido:** Analista de Tecnologia da Informação

**Projeto desenvolvido:** Projeto Desenvolvedor Back End

---

## Visão Geral

Este projeto consiste em uma API RESTful desenvolvida em Java com Spring Boot para o gerenciamento de artistas, álbuns e integração com serviços de armazenamento de objetos. A solução foca em boas práticas de engenharia de software, escalabilidade, segurança e conteinerização.

## Tecnologias e Arquitetura

O projeto foi construído utilizando as seguintes tecnologias principais:

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.2
* **Segurança:** Spring Security com autenticação Stateless via JWT
* **Banco de Dados:** H2 Database (In-Memory)
* **Versionamento de Banco de Dados:** Flyway Migrations
* **Armazenamento de Arquivos:** MinIO (Compatível com protocolo AWS S3)
* **Rate Limiting:** Bucket4j
* **Comunicação em Tempo Real:** WebSocket
* **Documentação:** OpenAPI 3.0 (Swagger UI)
* **Infraestrutura:** Docker e Docker Compose

## Funcionalidades Implementadas

### Segurança e Autenticação
* Autenticação via JWT (JSON Web Token) com expiração de 5 minutos.
* Controle de acesso baseado em roles (RBAC).
* Proteção contra acesso de domínios externos (CORS configurado).

### Gestão de Conteúdo (CRUD)
* Cadastro, consulta e edição de Artistas e Álbuns.
* Consultas parametrizadas (filtragem por tipo de artista: banda ou solo).
* Ordenação alfabética (ascendente/descendente) na listagem de artistas.
* Paginação otimizada na consulta de álbuns.

### Monitoramento e Versionamento
* Endpoints versionados (ex: /api/v1/...).
* Health checks via Spring Actuator.
* Documentação interativa via Swagger UI.

## Pré-requisitos

Para executar este projeto, certifique-se de ter instalado em sua máquina:

* Docker
* Docker Compose

## Instalação e Execução

O projeto está totalmente conteinerizado para facilitar a execução. Siga os passos abaixo:

1.  Clone o repositório para sua máquina local.

2.  Navegue até o diretório raiz do projeto via terminal.

3.  Execute o comando para compilar a aplicação e iniciar os contêineres (API e MinIO):

    ```bash
    docker-compose up --build
    ```

4.  Aguarde até que o log exiba a mensagem indicando que a aplicação iniciou (ex: "Started MusicApiApplication in...").

## Acesso aos Serviços

Após a inicialização, os seguintes serviços estarão disponíveis:

| Serviço | URL | Credenciais / Notas                                                                            |
| :--- | :--- |:-----------------------------------------------------------------------------------------------|
| **Swagger UI (Documentação)** | http://localhost:8080/swagger-ui.html | Utilize o token JWT para gerar token utilize: username: admin@test.com e password: password123 |
| **MinIO Console** | http://localhost:9001 | Usuário: `minioadmin` / Senha: `minioadmin`                                                    |                                                                                             
| **Console H2** | http://localhost:8080/h2-console | Driver Class: org.h2.Driver /  JDBC URL: `jdbc:h2:mem:musicdb` /User Name: sa                  |

## Guia Rápido de Teste

Para testar o fluxo completo da aplicação via Swagger ou Postman:

1.  **Registrar um Usuário:**
    Envie uma requisição POST para `/api/v1/auth/register` com os dados do usuário. Utilize: username: admin@test.com e password: password123.

2.  **Autenticar (Login):**
    Envie uma requisição POST para `/api/v1/auth/authenticate`. O retorno conterá o `access_token`.

3.  **Autorizar no Swagger:**
    Copie o token recebido. No Swagger UI, clique no botão "Authorize" e insira o valor no formato: `Bearer SEU_TOKEN_AQUI`.

4.  **Operações:**
    Agora você pode realizar chamadas para criar artistas, álbuns e fazer upload de imagens (POST `/api/v1/albums/{id}/cover`).

## Estrutura do Projeto

A organização do código fonte segue o padrão de camadas:

* **config:** Configurações globais (Segurança, Swagger, MinIO).
* **controller:** Controladores REST.
* **service:** Regras de negócio.
* **repository:** Interfaces de acesso a dados (JPA).
* **model:** Entidades do banco de dados.
* **dto:** Objetos de transferência de dados (Request/Response).
* **security:** Filtros e utilitários JWT.

## Decisões Arquiteturais e Design

Esta seção documenta as escolhas técnicas adotadas para garantir escalabilidade, segurança e performance da aplicação.

### Estratégia de Armazenamento (Offloading)
Foi adotado o padrão de URLs pré-assinadas (Presigned URLs) via MinIO. Em vez de trafegar arquivos binários (imagens) diretamente pela API Java, o backend gera apenas um link temporário e seguro para o cliente. Isso reduz drasticamente o consumo de memória da JVM e delega a transferência de dados pesados diretamente para o serviço de storage, permitindo que a API mantenha alta disponibilidade para regras de negócio.

### Otimização de Containers (Multi-Stage Build)
O Dockerfile utiliza a estratégia de Multi-Stage Build em dois estágios:
1.  **Build:** Utiliza uma imagem JDK completa e Maven para baixar dependências e compilar o projeto.
2.  **Runtime:** Utiliza uma imagem JRE Alpine minimalista para a execução.
    Isso resulta em uma imagem final leve, sem o código fonte exposto e com menor superfície de ataque.

### Autenticação Stateless
A arquitetura de segurança utiliza JWT (JSON Web Token) sem persistência de sessão no servidor. Cada requisição é autenticada de forma independente, o que facilita a escalabilidade horizontal da aplicação (adicionar novas instâncias da API) sem a necessidade de sincronização de sessões ou uso de sticky sessions no balanceador de carga.

### Estratégia de Testes
A garantia de qualidade foi dividida em níveis:
* **Testes de Integração:** Utilização de MockMvc para simular requisições HTTP reais e validar comportamentos de segurança (códigos 401/403) e configurações de CORS.
* **Testes Unitários:** Utilização de Mockito para isolar serviços externos (como o MinIO), garantindo que a lógica de negócio seja validada independentemente da disponibilidade da infraestrutura.

### Correções que não foram realizadas
Realizar o upload da imagem, somente via banco **BUCKET_NAME = "music-covers"**

### Capturas de Tela

Autenticação e geração Token
![imagem_1.png](imagens/imagem_1.png)