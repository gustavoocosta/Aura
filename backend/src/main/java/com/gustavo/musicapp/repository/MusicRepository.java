package com.gustavo.musicapp.repository;

import com.gustavo.musicapp.model.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    
    // Buscar por artista (case-insensitive)
    List<Music> findByArtistContainingIgnoreCase(String artist);
    
    // Buscar por gênero (case-insensitive)
    Page<Music> findByGenreContainingIgnoreCase(String genre, Pageable pageable);
    
    // Buscar por álbum
    List<Music> findByAlbumContainingIgnoreCase(String album);
    
    // Buscar por título, artista ou álbum (busca global)
    Page<Music> findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCaseOrAlbumContainingIgnoreCase(
            String title, String artist, String album, Pageable pageable);
    
    // Verificar se existe música com mesmo título e artista
    boolean existsByTitleAndArtist(String title, String artist);
    
    // Verificar se URL já está em uso
    boolean existsByUrl(String url);
    
    // Buscar música por URL
    Optional<Music> findByUrl(String url);
    
    // Buscar músicas por ano de lançamento
    List<Music> findByReleaseYear(Integer releaseYear);
    
    // Buscar músicas por faixa de anos
    List<Music> findByReleaseYearBetween(Integer startYear, Integer endYear);
    
    // Buscar músicas com duração específica (em segundos)
    List<Music> findByDurationSecondsBetween(Integer minDuration, Integer maxDuration);
    
    // Buscar músicas mais populares (com mais reproduções)
    @Query("SELECT m FROM Music m ORDER BY m.playCount DESC")
    Page<Music> findMostPopular(Pageable pageable);
    
    // Buscar músicas recentes
    @Query("SELECT m FROM Music m ORDER BY m.createdAt DESC")
    Page<Music> findMostRecent(Pageable pageable);
    
    // Obter todos os gêneros distintos
    @Query("SELECT DISTINCT m.genre FROM Music m WHERE m.genre IS NOT NULL ORDER BY m.genre")
    List<String> findDistinctGenres();
    
    // Obter todos os artistas distintos
    @Query("SELECT DISTINCT m.artist FROM Music m ORDER BY m.artist")
    List<String> findDistinctArtists();
    
    // Obter todos os álbuns distintos
    @Query("SELECT DISTINCT m.album FROM Music m WHERE m.album IS NOT NULL ORDER BY m.album")
    List<String> findDistinctAlbums();
    
    // Buscar músicas por artista e álbum
    List<Music> findByArtistAndAlbum(String artist, String album);
    
    // Contar músicas por artista
    @Query("SELECT COUNT(m) FROM Music m WHERE m.artist = :artist")
    Long countByArtist(@Param("artist") String artist);
    
    // Contar músicas por gênero
    @Query("SELECT COUNT(m) FROM Music m WHERE m.genre = :genre")
    Long countByGenre(@Param("genre") String genre);
    
    // Obter estatísticas de reprodução
    @Query("SELECT SUM(m.playCount) FROM Music m")
    Long getTotalPlayCount();
    
    // Buscar músicas com mais de X reproduções
    @Query("SELECT m FROM Music m WHERE m.playCount >= :minPlays ORDER BY m.playCount DESC")
    List<Music> findByMinPlayCount(@Param("minPlays") Long minPlays);
    
    // Buscar músicas sem URL definida
    List<Music> findByUrlIsNull();
    
    // Buscar músicas com URL definida
    List<Music> findByUrlIsNotNull();
    
    // Buscar artistas mais populares (com mais músicas)
    @Query("SELECT m.artist, COUNT(m) as musicCount FROM Music m GROUP BY m.artist ORDER BY musicCount DESC")
    List<Object[]> findMostPopularArtists(Pageable pageable);
    
    // Buscar gêneros mais populares
    @Query("SELECT m.genre, COUNT(m) as musicCount FROM Music m WHERE m.genre IS NOT NULL GROUP BY m.genre ORDER BY musicCount DESC")
    List<Object[]> findMostPopularGenres(Pageable pageable);
    
    // Buscar músicas por parte do título
    List<Music> findByTitleContainingIgnoreCase(String titlePart);
    
    // Buscar músicas ordenadas por número de reproduções
    List<Music> findAllByOrderByPlayCountDesc();
    
    // Buscar músicas ordenadas por data de criação
    List<Music> findAllByOrderByCreatedAtDesc();
}