package com.gustavo.musicapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "musics")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String artist;
    private String album;
    
    @Column(unique = true)
    private String url;  // Link da música armazenada
    
    // Getters e Setters
}