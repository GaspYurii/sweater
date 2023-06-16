package com.example.sweater.controller;

import com.example.sweater.model.Message;
import com.example.sweater.model.SecurityUser;
import com.example.sweater.model.User;
import com.example.sweater.model.dto.MessageDto;
import com.example.sweater.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping({"/messages", "/messages/"})
public class MessageController {
    public static final String MESSAGE = "message";
    public static final String MESSAGES = "messages";

    private final MessageService messageService;

    @GetMapping
    public String getMessages(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal SecurityUser currentUser
            ) {
        Page<MessageDto> page = messageService.getMessages(filter, pageable, currentUser.user());

        model.addAttribute("page", page);
        model.addAttribute("url", "/messages");
        model.addAttribute("filter", filter);

        return MESSAGES;
    }

    @PostMapping
    public String addMessage(
            @AuthenticationPrincipal SecurityUser currentUser,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        message.setAuthor(currentUser.user());

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute(MESSAGE, message);
        } else {
            messageService.saveFile(message, file);
            model.addAttribute(MESSAGE, null);
            messageService.saveMessage(message);
        }

        Iterable<Message> messages = messageService.getMessages();
        model.addAttribute(MESSAGES, messages);

        return "redirect:" + MESSAGES;
    }

    @GetMapping("/{user}")
    public String userMessages(
            @AuthenticationPrincipal SecurityUser currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<MessageDto> page = messageService.getMessagesOfUser(pageable, user);

        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser.user()));
        model.addAttribute("page", page);
        model.addAttribute(MESSAGE, message);
        model.addAttribute("isCurrentUser", currentUser.user().equals(user));
        model.addAttribute("url", "/messages/" + user.getId());

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
        messageService.updateMessage(currentUser.user(), message, text, tag, file);

        return "redirect:/messages/" + user;
    }

    @GetMapping("{message}/like")
    public String like(
            @AuthenticationPrincipal SecurityUser currentUser,
            @PathVariable Message message,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = message.getLikes();

        if (likes.contains(currentUser.user())) {
            likes.remove(currentUser.user());
        } else {
            likes.add(currentUser.user());
        }

        likes.contains(currentUser.user());

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }
}
