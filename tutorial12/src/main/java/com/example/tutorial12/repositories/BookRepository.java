package com.example.tutorial12.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tutorial12.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findByTitleContainingIgnoreCase(String title);
}