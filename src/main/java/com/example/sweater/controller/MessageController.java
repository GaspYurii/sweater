package com.example.sweater.controller;

import com.example.sweater.model.Message;
import com.example.sweater.model.SecurityUser;
import com.example.sweater.model.User;
import com.example.sweater.repository.MessageRepository;
import com.example.sweater.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping({"/messages", "/messages/"})
public class MessageController {
    public static final String MESSAGES ="messages";

    private final MessageRepository messageRepository;
    private final MessageService messageService;

    @GetMapping
    public String getMessages(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageService.getMessages(filter);

        model.addAttribute(MESSAGES, messages);
        return MESSAGES;
    }

    @PostMapping
    public String addMessage(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        messageService.addMessage(securityUser, message, bindingResult, model, file);

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute(MESSAGES, messages);

        return MESSAGES;
    }

    @GetMapping("/{user}")
    public String userMessages(
            @AuthenticationPrincipal SecurityUser currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message
    ) {
        Set<Message> messages = user.getMessages();

        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser.user()));
        model.addAttribute(MESSAGES, messages);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.user().equals(user));

        return "userMessages";
    }

    @PostMapping("/{user}")
    public String updateMessage(
            @AuthenticationPrincipal SecurityUser currentUser,
            @PathVariable Long user,
            @RequestParam(name = "id", required = false) Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        messageService.updateMessage(currentUser, message, text, tag, file);

        return "redirect:/messages/" + user;
    }

}
