package org.example.tutorial10.repository;

import org.example.tutorial10.model.Link;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Integer> {
}
