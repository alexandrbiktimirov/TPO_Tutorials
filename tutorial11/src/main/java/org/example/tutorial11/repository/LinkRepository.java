package org.example.tutorial11.repository;

import org.example.tutorial11.model.Link;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, String> {
}
