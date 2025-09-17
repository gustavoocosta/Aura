package com.gustavo.musicapp.controller;

import com.gustavo.musicapp.dto.MusicRequest;
import com.gustavo.musicapp.dto.MusicResponse;
import com.gustavo.musicapp.model.Music;
import com.gustavo.musicapp.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
@Tag(name = "Music", description = "API para gerenciar músicas")
@CrossOrigin(origins = "*")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping
    @Operation(summary = "Listar todas as músicas", description = "Retorna uma lista paginada de todas as músicas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de músicas retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<MusicResponse>> getAllMusics(
            @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MusicResponse> musics = musicService.getAllMusics(pageable);
        return ResponseEntity.ok(musics);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar música por ID", description = "Retorna uma música específica pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Música encontrada"),
        @ApiResponse(responseCode = "404", description = "Música não encontrada")
    })
    public ResponseEntity<MusicResponse> getMusicById(
            @Parameter(description = "ID da música") @PathVariable Long id) {
        MusicResponse music = musicService.getMusicById(id);
        return ResponseEntity.ok(music);
    }

    @PostMapping
    @Operation(summary = "Adicionar nova música", description = "Cria uma nova música no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Música criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Música já existe")
    })
    public ResponseEntity<MusicResponse> addMusic(@Valid @RequestBody MusicRequest musicRequest) {
        MusicResponse createdMusic = musicService.addMusic(musicRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMusic);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar música", description = "Atualiza os dados de uma música existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Música atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Música não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<MusicResponse> updateMusic(
            @Parameter(description = "ID da música") @PathVariable Long id,
            @Valid @RequestBody MusicRequest musicRequest) {
        MusicResponse updatedMusic = musicService.updateMusic(id, musicRequest);
        return ResponseEntity.ok(updatedMusic);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar música", description = "Remove uma música do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Música deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Música não encontrada")
    })
    public ResponseEntity<Void> deleteMusic(
            @Parameter(description = "ID da música") @PathVariable Long id) {
        musicService.deleteMusic(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar músicas", description = "Busca músicas por título, artista ou álbum")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultados da busca retornados"),
        @ApiResponse(responseCode = "400", description = "Parâmetros de busca inválidos")
    })
    public ResponseEntity<Page<MusicResponse>> searchMusics(
            @Parameter(description = "Termo de busca") @RequestParam String query,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MusicResponse> results = musicService.searchMusics(query, pageable);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/artist/{artist}")
    @Operation(summary = "Buscar músicas por artista", description = "Retorna todas as músicas de um artista específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Músicas do artista encontradas"),
        @ApiResponse(responseCode = "404", description = "Nenhuma música encontrada para o artista")
    })
    public ResponseEntity<List<MusicResponse>> getMusicsByArtist(
            @Parameter(description = "Nome do artista") @PathVariable String artist) {
        List<MusicResponse> musics = musicService.getMusicsByArtist(artist);
        return ResponseEntity.ok(musics);
    }

    @GetMapping("/genre/{genre}")
    @Operation(summary = "Buscar músicas por gênero", description = "Retorna todas as músicas de um gênero específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Músicas do gênero encontradas")
    })
    public ResponseEntity<Page<MusicResponse>> getMusicsByGenre(
            @Parameter(description = "Gênero musical") @PathVariable String genre,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MusicResponse> musics = musicService.getMusicsByGenre(genre, pageable);
        return ResponseEntity.ok(musics);
    }

    @PostMapping("/{id}/play")
    @Operation(summary = "Reproduzir música", description = "Incrementa o contador de reproduções da música")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reprodução registrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Música não encontrada")
    })
    public ResponseEntity<MusicResponse> playMusic(
            @Parameter(description = "ID da música") @PathVariable Long id) {
        MusicResponse music = musicService.playMusic(id);
        return ResponseEntity.ok(music);
    }

    @GetMapping("/popular")
    @Operation(summary = "Músicas mais populares", description = "Retorna as músicas mais reproduzidas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Músicas populares retornadas")
    })
    public ResponseEntity<List<MusicResponse>> getPopularMusics(
            @Parameter(description = "Limite de resultados") @RequestParam(defaultValue = "10") int limit) {
        List<MusicResponse> popularMusics = musicService.getPopularMusics(limit);
        return ResponseEntity.ok(popularMusics);
    }
}