package com.example.sweater.controllers;

import com.example.sweater.models.Message;
import com.example.sweater.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/messages")
    public String getMessages(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "messages";
    }

    @PostMapping("/messages")
    public String addMessages(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        messageRepository.save(new Message(text, tag));

        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "messages";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.put("messages", messages);
        return "messages";
    }
}
