package com.example.tutorial12.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PublisherID")
    private Integer publisherID;

    @Column(name = "PublisherName")
    private String publisherName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Country")
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    private Set<Book> books = new LinkedHashSet<>();

    public Publisher() {}

    public Publisher(String publisherName, String address, String country) {
        this.publisherName = publisherName;
        this.address = address;
        this.country = country;
    }

    public Integer getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(Integer publisherID) {
        this.publisherID = publisherID;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return publisherName + " " + address + " " + country;
    }
}