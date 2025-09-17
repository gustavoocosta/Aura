package com.gustavo.musicapp.service;

import com.gustavo.musicapp.dto.MusicRequest;
import com.gustavo.musicapp.dto.MusicResponse;
import com.gustavo.musicapp.exception.MusicNotFoundException;
import com.gustavo.musicapp.exception.DuplicateMusicException;
import com.gustavo.musicapp.model.Music;
import com.gustavo.musicapp.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicService {
    
    @Autowired
    private MusicRepository musicRepository;

    @Transactional(readOnly = true)
    public Page<MusicResponse> getAllMusics(Pageable pageable) {
        Page<Music> musics = musicRepository.findAll(pageable);
        return musics.map(MusicResponse::from);
    }

    @Transactional(readOnly = true)
    public MusicResponse getMusicById(Long id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new MusicNotFoundException("Música não encontrada com ID: " + id));
        return MusicResponse.from(music);
    }

    public MusicResponse addMusic(MusicRequest musicRequest) {
        // Verificar se já existe uma música com o mesmo título e artista
        if (musicRepository.existsByTitleAndArtist(musicRequest.getTitle(), musicRequest.getArtist())) {
            throw new DuplicateMusicException("Música já existe: " + musicRequest.getTitle() + " - " + musicRequest.getArtist());
        }
        
        // Verificar se URL já está em uso
        if (musicRequest.getUrl() != null && musicRepository.existsByUrl(musicRequest.getUrl())) {
            throw new DuplicateMusicException("URL já está sendo utilizada por outra música");
        }
        
        Music music = convertToEntity(musicRequest);
        Music savedMusic = musicRepository.save(music);
        return MusicResponse.from(savedMusic);
    }

    public MusicResponse updateMusic(Long id, MusicRequest musicRequest) {
        Music existingMusic = musicRepository.findById(id)
                .orElseThrow(() -> new MusicNotFoundException("Música não encontrada com ID: " + id));
        
        // Verificar duplicatas apenas se título ou artista foram alterados
        if (!existingMusic.getTitle().equals(musicRequest.getTitle()) || 
            !existingMusic.getArtist().equals(musicRequest.getArtist())) {
            if (musicRepository.existsByTitleAndArtist(musicRequest.getTitle(), musicRequest.getArtist())) {
                throw new DuplicateMusicException("Já existe uma música com este título e artista");
            }
        }
        
        // Verificar URL duplicada apenas se foi alterada
        if (musicRequest.getUrl() != null && !musicRequest.getUrl().equals(existingMusic.getUrl())) {
            if (musicRepository.existsByUrl(musicRequest.getUrl())) {
                throw new DuplicateMusicException("URL já está sendo utilizada por outra música");
            }
        }
        
        // Atualizar os campos
        updateMusicFields(existingMusic, musicRequest);
        Music updatedMusic = musicRepository.save(existingMusic);
        return MusicResponse.from(updatedMusic);
    }

    public void deleteMusic(Long id) {
        if (!musicRepository.existsById(id)) {
            throw new MusicNotFoundException("Música não encontrada com ID: " + id);
        }
        musicRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<MusicResponse> searchMusics(String query, Pageable pageable) {
        Page<Music> musics = musicRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCaseOrAlbumContainingIgnoreCase(
                query, query, query, pageable);
        return musics.map(MusicResponse::from);
    }

    @Transactional(readOnly = true)
    public List<MusicResponse> getMusicsByArtist(String artist) {
        List<Music> musics = musicRepository.findByArtistContainingIgnoreCase(artist);
        return musics.stream()
                .map(MusicResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<MusicResponse> getMusicsByGenre(String genre, Pageable pageable) {
        Page<Music> musics = musicRepository.findByGenreContainingIgnoreCase(genre, pageable);
        return musics.map(MusicResponse::from);
    }

    public MusicResponse playMusic(Long id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new MusicNotFoundException("Música não encontrada com ID: " + id));
        
        music.incrementPlayCount();
        Music updatedMusic = musicRepository.save(music);
        return MusicResponse.from(updatedMusic);
    }

    @Transactional(readOnly = true)
    public List<MusicResponse> getPopularMusics(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "playCount"));
        Page<Music> popularMusics = musicRepository.findAll(pageable);
        return popularMusics.getContent().stream()
                .map(MusicResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MusicResponse> getRecentMusics(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Music> recentMusics = musicRepository.findAll(pageable);
        return recentMusics.getContent().stream()
                .map(MusicResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> getAllGenres() {
        return musicRepository.findDistinctGenres();
    }

    @Transactional(readOnly = true)
    public List<String> getAllArtists() {
        return musicRepository.findDistinctArtists();
    }

    // Métodos auxiliares privados
    private Music convertToEntity(MusicRequest musicRequest) {
        Music music = new Music();
        music.setTitle(musicRequest.getTitle());
        music.setArtist(musicRequest.getArtist());
        music.setAlbum(musicRequest.getAlbum());
        music.setUrl(musicRequest.getUrl());
        music.setDurationSeconds(musicRequest.getDurationSeconds());
        music.setGenre(musicRequest.getGenre());
        music.setReleaseYear(musicRequest.getReleaseYear());
        return music;
    }

    private void updateMusicFields(Music music, MusicRequest musicRequest) {
        music.setTitle(musicRequest.getTitle());
        music.setArtist(musicRequest.getArtist());
        music.setAlbum(musicRequest.getAlbum());
        music.setUrl(musicRequest.getUrl());
        music.setDurationSeconds(musicRequest.getDurationSeconds());
        music.setGenre(musicRequest.getGenre());
        music.setReleaseYear(musicRequest.getReleaseYear());
    }
}