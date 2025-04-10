package org.example.tutorial4.repository;

import org.example.tutorial4.entity.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
