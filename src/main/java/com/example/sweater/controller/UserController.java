package com.example.sweater.controller;

import com.example.sweater.model.Role;
import com.example.sweater.model.SecurityUser;
import com.example.sweater.model.User;
import com.example.sweater.repository.UserRepository;
import com.example.sweater.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @GetMapping("/users/{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editUser(
            Model model,
            @PathVariable User user
    ) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveUser(
            @RequestParam String username,
            @RequestParam("userId") User user,
            @RequestParam Map<String, String> form
    ) {
        userService.saveUser(user, username, form);
        return "redirect:users";
    }

    @GetMapping("/profile")
    public String getProfile(
            @AuthenticationPrincipal SecurityUser currentUser,
            Model model
    ) {
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("email", currentUser.getEmail());

        return "profile";
    }
    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal SecurityUser currentUser,
            @RequestParam("password") String password,
            @RequestParam("email") String email
    ) {
            userService.updateProfile(currentUser.getUser(), password, email);

        return "redirect:profile";
    }
}
