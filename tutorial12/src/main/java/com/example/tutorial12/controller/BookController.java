package com.example.tutorial12.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.tutorial12.model.Book;
import com.example.tutorial12.model.Review;
import com.example.tutorial12.services.BookDtoMapper;
import com.example.tutorial12.services.BookService;
import com.example.tutorial12.services.ReviewService;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookDtoMapper bookDtoMapper;
    private final ReviewService reviewService;

    public BookController(BookService bookService, BookDtoMapper bookDtoMapper, ReviewService reviewService) {
        this.bookService = bookService;
        this.bookDtoMapper = bookDtoMapper;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String listBooks(
            @RequestParam(name="search", required=false) String search,
            Model model
    ) {
        List<Book> books;
        if (search != null && !search.isEmpty()) {
            books = bookService.searchByTitle(search);
        } else {
            books = bookService.getAllBooks();
        }
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/{id}")
    public String bookDetails(@PathVariable("id") Integer id, Model model) {
        Book book = bookService.getBookEntityById(id).orElse(null);

        if (book == null) {
            model.addAttribute("errorMessage", "Book not found");
            return "redirect:/books";
        }

        List<Review> reviews = reviewService.findByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);

        return "books-details";
    }
}