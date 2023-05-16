package com.example.sweater.controller;

import com.example.sweater.constant.UrlPath;
import com.example.sweater.model.User;
import com.example.sweater.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping(UrlPath.REGISTRATION)
    public String registration() {
        return "registration";
    }

    @PostMapping(UrlPath.REGISTRATION)
    public String registration(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("message", "The user already exists");
            return "registration";
        }

        return "redirect:login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
