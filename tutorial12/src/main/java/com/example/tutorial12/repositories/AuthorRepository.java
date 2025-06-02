package com.example.tutorial12.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tutorial12.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);

    List<Author> findAll();
}