package com.gustavo.musicapp.controller;

import com.gustavo.musicapp.model.Music;
import com.gustavo.musicapp.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping
    public List<Music> getAllMusics() {
        return musicService.getAllMusics();
    }

    @PostMapping
    public ResponseEntity<Music> addMusic(@RequestBody Music music) {
        return ResponseEntity.ok(musicService.addMusic(music));
    }
}