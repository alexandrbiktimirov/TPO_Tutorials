package com.example.tutorial12.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tutorial12.model.Genre;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Integer> {
    Optional<Genre> findGenreByGenreName(String name);
}