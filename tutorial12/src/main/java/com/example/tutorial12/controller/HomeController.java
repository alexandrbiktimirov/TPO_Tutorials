package com.example.tutorial12.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.tutorial12.model.Book;
import com.example.tutorial12.services.BookService;

@Controller
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<Book> books = bookService.getAllBooks();

        model.addAttribute("books", books);
        return "home";
    }
}