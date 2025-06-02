package com.example.tutorial12.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.tutorial12.model.Role;
import com.example.tutorial12.repositories.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}