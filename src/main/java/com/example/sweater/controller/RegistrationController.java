package com.example.sweater.controller;

import com.example.sweater.model.User;
import com.example.sweater.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
public class RegistrationController {
    private static final String REGISTRATION = "registration";
    public static final String MESSAGE = "message";

    private final UserService userService;

    @GetMapping("/" + REGISTRATION)
    public String registration() {
        return REGISTRATION;
    }

    @PostMapping("/" + REGISTRATION)
    public String registration(
            @RequestParam("password2") String passwordConfirmation,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        boolean isPasswordConfirmationEmpty = StringUtils.isEmpty(passwordConfirmation);
        if (isPasswordConfirmationEmpty) {
            model.addAttribute("passwordError", "Password confirmation cannot be empty");
        }

        boolean isPasswordConfirmed = user.getPassword().equals(passwordConfirmation);
        if (!isPasswordConfirmed) {
            model.addAttribute("password2Error", "Passwords are different!");
        }

        if (isPasswordConfirmationEmpty ||
                !isPasswordConfirmed ||
                bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrorsMap(bindingResult);

            model.mergeAttributes(errors);

            return REGISTRATION;
        }

        if (!userService.createUser(user)) {
            model.addAttribute(MESSAGE, "The user already exists");
            return REGISTRATION;
        }

        return "redirect:login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute(MESSAGE, "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute(MESSAGE, "Activation code is not found!");
        }

        return "login";
    }
}
