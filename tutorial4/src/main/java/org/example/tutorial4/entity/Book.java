package org.example.tutorial4.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="price")
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="publisher_id")
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Author_Book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getAuthorsNames(){
        StringBuilder result = new StringBuilder();

        for(Author author : authors){
            result.append(author.getFirstName()).append(" ").append(author.getLastName()).append(",");
        }

        if(!result.isEmpty()){
            result.setCharAt(result.length() - 1, '.');
        }

        return result.toString();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book #" + id + ". Title: " + title + ". Price: " + price + "PLN. Authors: " + getAuthorsNames() + " Publisher: " + publisher.getName();
    }
}
