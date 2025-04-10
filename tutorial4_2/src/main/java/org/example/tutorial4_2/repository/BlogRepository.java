package org.example.tutorial4_2.repository;

import org.example.tutorial4_2.entity.Blog;
import org.example.tutorial4_2.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends CrudRepository<Blog, Long> {
    List<Blog> findAll();

    Optional<Blog> findByName(String name);

    Optional<Blog> findByManager(User manager);
}
