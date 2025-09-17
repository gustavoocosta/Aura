package com.gustavo.musicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "musics", indexes = {
    @Index(name = "idx_artist", columnList = "artist"),
    @Index(name = "idx_album", columnList = "album"),
    @Index(name = "idx_title", columnList = "title")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Music {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200, message = "Título deve ter no máximo 200 caracteres")
    @Column(nullable = false, length = 200)
    private String title;
    
    @NotBlank(message = "Artista é obrigatório")
    @Size(max = 150, message = "Nome do artista deve ter no máximo 150 caracteres")
    @Column(nullable = false, length = 150)
    private String artist;
    
    @Size(max = 150, message = "Nome do álbum deve ter no máximo 150 caracteres")
    @Column(length = 150)
    private String album;
    
    @Column(unique = true, length = 500)
    private String url;  // Link da música armazenada
    
    @Column(name = "duration_seconds")
    private Integer durationSeconds;
    
    @Size(max = 100, message = "Gênero deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String genre;
    
    @Column(name = "release_year")
    private Integer releaseYear;
    
    @Column(name = "play_count", columnDefinition = "BIGINT DEFAULT 0")
    private Long playCount = 0L;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public Music() {}
    
    public Music(String title, String artist, String album, String url) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.url = url;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public String getAlbum() {
        return album;
    }
    
    public void setAlbum(String album) {
        this.album = album;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Integer getDurationSeconds() {
        return durationSeconds;
    }
    
    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public Integer getReleaseYear() {
        return releaseYear;
    }
    
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
    
    public Long getPlayCount() {
        return playCount;
    }
    
    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Business methods
    public void incrementPlayCount() {
        this.playCount = (this.playCount == null ? 0 : this.playCount) + 1;
    }
    
    public String getFormattedDuration() {
        if (durationSeconds == null) return "00:00";
        int minutes = durationSeconds / 60;
        int seconds = durationSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    // equals, hashCode and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return Objects.equals(id, music.id) && 
               Objects.equals(title, music.title) && 
               Objects.equals(artist, music.artist);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, title, artist);
    }
    
    @Override
    public String toString() {
        return String.format("Music{id=%d, title='%s', artist='%s', album='%s'}", 
                           id, title, artist, album);
    }
}