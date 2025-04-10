package org.example.tutorial4_2.service;

import jakarta.transaction.Transactional;
import org.example.tutorial4_2.entity.Role;
import org.example.tutorial4_2.entity.User;
import org.example.tutorial4_2.exceptions.UserNotFoundException;
import org.example.tutorial4_2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public void createNewUser(User user){
        userRepository.save(user);
    }

    public User findUser(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void deleteUser(String email){
        try{
            userRepository.delete(findUser(email));
        } catch (UserNotFoundException ignored){}
    }

    public Optional<User> findUserById(long id){
        return userRepository.findById(id);
    }

    @Transactional
    public void detachRole(Role role){
        for (User user : new HashSet<>(role.getUsers())) {
            user.getRoles().remove(role);
            userRepository.save(user);
        }
    }
}
