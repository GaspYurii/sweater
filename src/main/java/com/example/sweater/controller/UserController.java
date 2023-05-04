package com.example.sweater.controller;

import com.example.sweater.model.Role;
import com.example.sweater.model.User;
import com.example.sweater.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        List<User> users = userRepository.findAll();
        model.put("users", users);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String username, @RequestParam String password,  Map<String, Object> model) {
        userRepository.save(new User(username, encoder.encode(password), Role.ADMIN));
        List<User> users = userRepository.findAll();
        model.put("users", users);
        return "redirect:login";
    }

}
