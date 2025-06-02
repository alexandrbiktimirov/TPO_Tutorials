package com.example.tutorial12.repositories;

import java.util.List;
import java.util.Optional;
import com.example.tutorial12.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}