package org.example.tutorial4_2.repository;

import org.example.tutorial4_2.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findAll();

    Optional<Role> findByName(String name);
}
