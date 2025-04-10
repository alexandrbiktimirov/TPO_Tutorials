package org.example.tutorial4_2.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Article")
public class Article {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @ManyToOne
    @JoinColumn(name="author")
    private User author;

    @ManyToOne
    @JoinColumn(name="blog")
    private Blog blog;

    public Article() {

    }

    public Article(String title) {
        this.title = title;
    }

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        if(blog == null && author == null){
            return "Article #" + id + ". Title: " + title + ". Author: none. Blog: none";
        }
        if(author == null){
            return "Article #" + id + ". Title: " + title + ". Author: none"  + ". Blog: " + blog.getName();
        }
        if(blog == null){
            return "Article #" + id + ". Title: " + title + ". Author: " + author.getEmail() + ". Blog: none";
        }

        return "Article #" + id + ". Title: " + title + ". Author: " + author.getEmail() + ". Blog: " + blog.getName();
    }
}
