package com.example.tutorial12.services;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tutorial12.model.Role;
import com.example.tutorial12.model.User;
import com.example.tutorial12.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final Map<String, PasswordEncoder> passwordEncoders;

    private final Random random = new Random();

    public UserService(Map<String, PasswordEncoder> passwordEncoders, RoleService roleService, UserRepository userRepository) {
        this.passwordEncoders = passwordEncoders;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerReader(User user) {
        Role readerRole = roleService.findByName("ROLE_READER");
        if (readerRole == null) {
            throw new RuntimeException("ROLE_READER not found");
        }
        user.setRoles(new HashSet<>(Arrays.asList(readerRole)));
        encodePasswordRandomly(user);
        return userRepository.save(user);
    }

    @Transactional
    public User registerPublisher(User user) {
        Role publisherRole = roleService.findByName("ROLE_PUBLISHER");
        Role readerRole    = roleService.findByName("ROLE_READER");
        if (publisherRole == null || readerRole == null) {
            throw new RuntimeException("Missing roles in DB");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(readerRole);
        roles.add(publisherRole);
        user.setRoles(roles);
        encodePasswordRandomly(user);
        return userRepository.save(user);
    }

    private void encodePasswordRandomly(User user) {
        String raw = user.getPassword();
        String[] ids = {"bcrypt", "scrypt", "pbkdf2", "argon2"};
        String id = ids[random.nextInt(ids.length)];
        PasswordEncoder encoder = passwordEncoders.get(id);
        if (encoder == null) {
            throw new RuntimeException("Encoder not found for id=" + id);
        }
        String encodedRaw = encoder.encode(raw);
        user.setPassword("{" + id + "}" + encodedRaw);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role r : u.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                authorities
        );
    }
}