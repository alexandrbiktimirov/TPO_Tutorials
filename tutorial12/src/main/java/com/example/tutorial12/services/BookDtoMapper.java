package com.example.tutorial12.services;

import org.springframework.stereotype.Service;
import com.example.tutorial12.model.*;
import com.example.tutorial12.repositories.AuthorRepository;
import com.example.tutorial12.repositories.GenreRepository;
import com.example.tutorial12.repositories.PublisherRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookDtoMapper {

    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookDtoMapper(PublisherRepository publisherRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public BookDTO map(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookID(book.getBookID());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublicationYear(book.getPublicationYear());
        bookDTO.setPublisher(book.getPublisher().getPublisherName() + "\t" + book.getPublisher().getAddress() + "\t" + book.getPublisher().getCountry());
        bookDTO.setAuthor(book.getAuthor().getFirstName() + "\t" + book.getAuthor().getLastName());
        bookDTO.setGenres(book.getGenres().stream().map(e -> e.getGenreName()).collect(Collectors.toSet()));
        return bookDTO;
    }

    public Book map(BookDTO bookDTO) {
        Book book = new Book();
        book.setBookID(bookDTO.getBookID());
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());

        String[] publisherData = bookDTO.getPublisher().split("\t");
        book.setPublisher(publisherRepository
                .findPublisherByPublisherNameAndAddressAndCountry(publisherData[0], publisherData[1], publisherData[2])
                .orElse(new Publisher(publisherData[0], publisherData[1], publisherData[2]))
        );

        String[] authorData = bookDTO.getAuthor().split("\t");
        book.setAuthor(authorRepository
                .findAuthorByFirstNameAndLastName(authorData[0], authorData[1])
                .orElse(new Author(authorData[0], authorData[1]))
        );

        book.setGenres(bookDTO.getGenres().stream()
                .map(genreName -> genreRepository.findGenreByGenreName(genreName)
                        .orElse(new Genre(genreName))
                ).collect(Collectors.toSet()));

        return book;
    }

}
