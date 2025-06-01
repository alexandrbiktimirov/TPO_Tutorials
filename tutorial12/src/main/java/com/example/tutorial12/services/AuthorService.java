package com.example.tutorial12.services;

import org.springframework.stereotype.Service;
import com.example.tutorial12.model.Author;
import com.example.tutorial12.repositories.AuthorRepository;

import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> getAuthor(String firstName, String lastName) {
        return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);
    }

}
