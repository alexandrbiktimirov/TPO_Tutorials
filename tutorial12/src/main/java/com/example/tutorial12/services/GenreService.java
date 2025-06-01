package com.example.tutorial12.services;

import org.springframework.stereotype.Service;
import com.example.tutorial12.model.Genre;
import com.example.tutorial12.repositories.GenreRepository;

import java.util.Optional;

@Service
public class GenreService {

    private GenreRepository genreRepository;
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<Genre> getGenre(String name) {
        return genreRepository.findGenreByGenreName(name);
    }

}
