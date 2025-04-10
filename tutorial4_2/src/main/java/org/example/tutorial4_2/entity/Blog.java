package org.example.tutorial4_2.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "blog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Article> articles = new HashSet<>();

    @OneToOne
    @JoinColumn(name="manager")
    private User manager;

    public Blog() {
    }

    public Blog(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        if (manager != null) {
            return "Blog #" + id + ". Name: " + name + ". Manager: " + manager.getEmail();
        }

        return "Blog #" + id + ". Name: " + name + ". Manager: none";
    }
}
