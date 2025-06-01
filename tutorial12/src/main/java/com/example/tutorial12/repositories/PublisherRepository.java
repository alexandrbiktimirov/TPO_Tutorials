package com.example.tutorial12.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tutorial12.model.Publisher;

import java.util.Optional;

public interface PublisherRepository extends CrudRepository<Publisher, Integer> {
    Optional<Publisher> findPublisherByPublisherNameAndAddressAndCountry(String name, String address, String country);
}