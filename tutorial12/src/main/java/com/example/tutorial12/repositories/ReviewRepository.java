package com.example.tutorial12.repositories;

import java.util.List;
import com.example.tutorial12.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByBookId(Integer bookId);
    List<Review> findByUserId(Long userId);
}