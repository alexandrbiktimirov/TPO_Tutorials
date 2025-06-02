package com.example.tutorial12.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import com.example.tutorial12.model.Book;
import com.example.tutorial12.model.Review;
import com.example.tutorial12.model.User;
import com.example.tutorial12.services.BookService;
import com.example.tutorial12.services.ReviewService;
import com.example.tutorial12.services.UserService;

@Controller
@RequestMapping("/reader")
public class ReaderController {

    private final BookService bookService;
    private final ReviewService reviewService;
    private final UserService userService;

    public ReaderController(BookService bookService, UserService userService, ReviewService reviewService) {
        this.bookService = bookService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("/books/{bookId}/review")
    public String showAddReviewForm(@PathVariable("bookId") Integer bookId, Model model) {
        Book book = bookService.getBookEntityById(bookId).orElse(null);

        if (book == null) {
            model.addAttribute("errorMessage", "Book not found");
            return "redirect:/books";
        }

        model.addAttribute("review", new Review());
        model.addAttribute("bookId", bookId);

        return "reader-add-review";
    }

    @PostMapping("/books/{bookId}/review")
    public String addReview(
            @PathVariable("bookId") Integer bookId,
            @Valid @ModelAttribute("review") Review review,
            BindingResult result,
            Model model
    ) {
        Book book = bookService.getBookEntityById(bookId).orElse(null);

        if (book == null) {
            model.addAttribute("errorMessage", "Book not found");
            return "redirect:/books";
        }

        if (result.hasErrors()) {
            model.addAttribute("bookId", bookId);
            return "reader-add-review";
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        review.setBook(book);
        review.setUser(user);
        reviewService.save(review);

        return "redirect:/books/" + bookId;
    }

    @GetMapping("/reviews")
    public String myReviews(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        List<Review> reviews = reviewService.findByUser(user);
        model.addAttribute("reviews", reviews);

        return "reader-my-reviews";
    }
}