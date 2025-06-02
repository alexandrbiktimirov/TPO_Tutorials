package com.example.tutorial12.repositories;

import java.util.List;
import java.util.Optional;
import com.example.tutorial12.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
    List<Role> findAll();
}