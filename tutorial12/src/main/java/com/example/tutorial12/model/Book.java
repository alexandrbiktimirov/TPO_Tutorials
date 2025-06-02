package com.example.tutorial12.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookID")
    private Integer id;

    @Column(name = "Title")
    private String title;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "PublicationYear")
    private Integer publicationYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AuthorID")
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PublisherID")
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "BookGenre",
            joinColumns = { @JoinColumn(name = "bookID") },
            inverseJoinColumns = { @JoinColumn(name = "genreID") }
    )
    private Set<Genre> genres = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + id +
                ", title=" + title +
                ", isbn=" + isbn +
                ", publicationYear=" + publicationYear +
                ", author=" + author.getFirstName() + " " + author.getLastName() +
                ", publisher=" + publisher.getPublisherName() + " " + publisher.getAddress() + " " + publisher.getCountry() +
                ", genres=" + genres +
                '}';
    }
}