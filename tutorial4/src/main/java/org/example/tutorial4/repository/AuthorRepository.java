package org.example.tutorial4.repository;

import org.example.tutorial4.entity.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
