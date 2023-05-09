package com.example.sweater.controller;

import com.example.sweater.constant.UrlPath;
import com.example.sweater.model.User;
import com.example.sweater.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(UrlPath.REGISTRATION)
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String registration(@RequestParam String username, @RequestParam String password, Model model) {
        userRepository.save(new User(username, encoder.encode(password)));
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "redirect:login";
    }
}
