package org.example.tutorial4_2.repository;

import org.example.tutorial4_2.entity.Article;
import org.example.tutorial4_2.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAll();

    Optional<Article> findByTitle(String title);

    List<Article> findAllByAuthor(User author);
}
