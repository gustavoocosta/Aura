package com.gustavo.musicapp.service;

import com.gustavo.musicapp.model.Music;
import com.gustavo.musicapp.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    public List<Music> getAllMusics() {
        return musicRepository.findAll();
    }

    public Music addMusic(Music music) {
        return musicRepository.save(music);
    }
}