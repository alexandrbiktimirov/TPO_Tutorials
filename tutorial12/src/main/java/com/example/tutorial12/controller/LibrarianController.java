package com.example.tutorial12.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import com.example.tutorial12.model.Author;
import com.example.tutorial12.model.Genre;
import com.example.tutorial12.model.Publisher;

import com.example.tutorial12.services.AuthorService;
import com.example.tutorial12.services.GenreService;
import com.example.tutorial12.services.PublisherService;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final PublisherService publisherService;

    public LibrarianController(AuthorService authorService, GenreService genreService, PublisherService publisherService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.publisherService = publisherService;
    }

    @GetMapping("/authors")
    public String listAuthors(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "librarian-authors";
    }

    @GetMapping("/authors/add")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "librarian-add-author";
    }

    @PostMapping("/authors/add")
    public String addAuthor(
            @Valid @ModelAttribute("author") Author author,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "librarian-add-author";
        }
        authorService.save(author);
        return "redirect:/librarian/authors";
    }

    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable Integer id) {
        authorService.deleteById(id);

        return "redirect:/librarian/authors";
    }

    @GetMapping("/genres")
    public String listGenres(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        return "librarian-genres";
    }

    @GetMapping("/genres/add")
    public String showAddGenreForm(Model model) {
        model.addAttribute("genre", new Genre());

        return "librarian-add-genre";
    }

    @PostMapping("/genres/add")
    public String addGenre(
            @Valid @ModelAttribute("genre") Genre genre,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "librarian-add-genre";
        }
        genreService.save(genre);
        return "redirect:/librarian/genres";
    }

    @GetMapping("/genres/delete/{id}")
    public String deleteGenre(@PathVariable Integer id) {
        genreService.deleteById(id);
        return "redirect:/librarian/genres";
    }

    @GetMapping("/publishers")
    public String listPublishers(Model model) {
        List<Publisher> pubs = publisherService.findAll();
        model.addAttribute("publishers", pubs);
        return "librarian-publishers";
    }

    @GetMapping("/publishers/add")
    public String showAddPublisherForm(Model model) {
        model.addAttribute("publisher", new Publisher());

        return "librarian-add-publisher";
    }

    @PostMapping("/publishers/add")
    public String addPublisher(
            @Valid @ModelAttribute("publisher") Publisher publisher,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "librarian-add-publisher";
        }

        publisherService.save(publisher);

        return "redirect:/librarian/publishers";
    }

    @GetMapping("/publishers/delete/{id}")
    public String deletePublisher(@PathVariable Integer id) {
        publisherService.deleteById(id);

        return "redirect:/librarian/publishers";
    }
}