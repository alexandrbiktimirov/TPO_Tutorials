package com.example.tutorial12.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tutorial12.model.Review;
import com.example.tutorial12.model.User;
import com.example.tutorial12.repositories.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> findByBookId(Integer bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    public List<Review> findByUser(User user) {
        return reviewRepository.findByUserId(user.getId());
    }
}