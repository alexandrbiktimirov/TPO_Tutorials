package org.example.tutorial4.repository;

import org.example.tutorial4.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
