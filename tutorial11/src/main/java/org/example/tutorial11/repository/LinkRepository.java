package org.example.tutorial11.repository;

import org.example.tutorial11.model.Link;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LinkRepository extends CrudRepository<Link, String> {
    public Optional<Link> findLinkByTargetUrl(String targetUrl);
}
