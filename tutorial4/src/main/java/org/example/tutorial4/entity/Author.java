package org.example.tutorial4.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNamesOfBooks(){
        StringBuilder result = new StringBuilder();

        for(Book book : books){
            result.append(book.getTitle()).append(",");
        }

        if(!result.isEmpty()){
            result.setCharAt(result.length() - 1, '.');
        }

        return result.toString();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author #" + id + ". First name: " + firstName + ". Last name: " + lastName + ". Books: " + getNamesOfBooks();
    }
}