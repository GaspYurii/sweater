package com.example.sweater.controller;

import com.example.sweater.constant.UrlPath;
import com.example.sweater.model.Message;
import com.example.sweater.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepository;

    @GetMapping(UrlPath.MESSAGES)
    public String messages(Map<String, Object> model) {
        initMessages(model);
        return "messages";
    }

    @PostMapping(UrlPath.MESSAGES)
    public String addMessage(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        messageRepository.save(new Message(text, tag));

        initMessages(model);
        return "messages";
    }

    @PostMapping(UrlPath.FILTER)
    @PreAuthorize("hasRole('ADMIN')")
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

    private void initMessages(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
    }
}
