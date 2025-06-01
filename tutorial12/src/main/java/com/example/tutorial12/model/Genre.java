package com.example.tutorial12.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GenreID")
    private Integer id;

    @Column(name = "GenreName")
    private String genreName;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
    private Set<Book> books = new LinkedHashSet<>();

    public Genre(){}

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return genreName;
    }
}