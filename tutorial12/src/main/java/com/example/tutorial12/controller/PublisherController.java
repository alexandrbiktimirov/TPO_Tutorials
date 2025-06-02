package com.example.tutorial12.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import com.example.tutorial12.model.Book;

import com.example.tutorial12.services.AuthorService;
import com.example.tutorial12.services.BookService;
import com.example.tutorial12.services.GenreService;
import com.example.tutorial12.services.PublisherService;

@Controller
@RequestMapping("/publisher")
public class PublisherController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final PublisherService publisherService;

    public PublisherController(BookService bookService, AuthorService authorService, GenreService genreService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.publisherService = publisherService;
    }

    @GetMapping("/books")
    public String listMyBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "publisher-books";
    }

    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("publishers", publisherService.findAll());

        return "publisher-add-book";
    }

    @PostMapping("/books/add")
    public String addBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("publishers", publisherService.findAll());
            return "publisher-add-book";
        }
        bookService.saveEntity(book);
        return "redirect:/publisher/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditBookForm(@PathVariable Integer id, Model model) {
        Book book = bookService.getBookEntityById(id).orElse(null);

        if (book == null) {
            model.addAttribute("errorMessage", "Book not found");
            return "redirect:/publisher/books";
        }

        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("publishers", publisherService.findAll());

        return "publisher-edit-book";
    }

    @PostMapping("/books/edit/{id}")
    public String editBook(
            @PathVariable Integer id,
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("publishers", publisherService.findAll());
            return "publisher-edit-book";
        }

        book.setId(id);
        bookService.saveEntity(book);

        return "redirect:/publisher/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteEntityById(id);

        return "redirect:/publisher/books";
    }
}