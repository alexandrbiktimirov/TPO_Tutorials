package com.example.tutorial12.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.tutorial12.model.User;
import com.example.tutorial12.services.UserService;

@Controller
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register/reader")
    public String showReaderRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register-reader";
    }

    @PostMapping("/register/reader")
    public String registerReader(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "register-reader";
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errorMessage", "Username already exists");
            return "register-reader";
        }
        userService.registerReader(user);
        return "redirect:/login?registerSuccess";
    }

    @GetMapping("/register/publisher")
    public String showPublisherRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "register-publisher";
    }

    @PostMapping("/register/publisher")
    public String registerPublisher(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "register-publisher";
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errorMessage", "Username already exists");
            return "register-publisher";
        }

        userService.registerPublisher(user);
        return "redirect:/login?registerSuccess";
    }
}