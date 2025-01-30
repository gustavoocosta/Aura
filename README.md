# Music Streaming App 🎵

Um sistema de streaming de música estilo Spotify, desenvolvido em Java com Spring Boot.

## Tecnologias Utilizadas

- Java + Spring Boot
- MySQL + JPA
- Spring Security (Autenticação JWT)
- API RESTful

## Como Rodar o Projeto

1. Instale o MySQL e crie o banco de dados `musicapp`
2. Configure o `application.yml` com suas credenciais
3. Rode o projeto com:
   ```sh
   mvn spring-boot:run
   ```

## Endpoints Principais

- `GET /api/music` - Lista todas as músicas
- `POST /api/music` - Adiciona uma nova música

## Futuras Melhorias

- Upload de músicas (AWS S3)
- Playlists e curtidas
- Frontend em React

🚀 Desenvolvido por Gustavo Costa.
