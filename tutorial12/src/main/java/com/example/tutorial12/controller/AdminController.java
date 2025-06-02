package com.example.tutorial12.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import com.example.tutorial12.model.Role;
import com.example.tutorial12.model.User;

import com.example.tutorial12.services.RoleService;
import com.example.tutorial12.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        List<Role> roles = roleService.findAll();

        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        return "admin-manage-roles";
    }

    @PostMapping("/users/{userId}/roles")
    public String updateRoles(
            @PathVariable("userId") Long userId,
            @RequestParam(name="roleNames", required=false) List<String> roleNames
    ) {
        User user = userService.findById(userId);

        if (user == null) {
            return "redirect:/admin/users?error=UserNotFound";
        }

        Set<Role> newRoles = new HashSet<>();

        if (roleNames != null) {
            for (String rn : roleNames) {
                Role r = roleService.findByName(rn);
                if (r != null) {
                    newRoles.add(r);
                }
            }
        }

        user.setRoles(newRoles);
        userService.save(user);

        return "redirect:/admin/users";
    }
}