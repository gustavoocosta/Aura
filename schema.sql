CREATE DATABASE musicapp;

\c musicapp;

CREATE TABLE musics (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    album VARCHAR(255),
    release_date DATE,
    genre VARCHAR(100)
);
