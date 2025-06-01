package com.example.tutorial12.services;

import org.springframework.stereotype.Service;
import com.example.tutorial12.model.Author;
import com.example.tutorial12.model.Publisher;
import com.example.tutorial12.repositories.PublisherRepository;

import java.util.Optional;

@Service
public class PublisherService {

    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Optional<Publisher> getPublisher(String name, String address, String country) {
        return publisherRepository.findPublisherByPublisherNameAndAddressAndCountry(name, address, country);
    }

}
