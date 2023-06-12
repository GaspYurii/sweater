package com.example.sweater.service;

import com.example.sweater.model.Message;
import com.example.sweater.model.User;
import com.example.sweater.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Iterable<Message> getMessages(String filter) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        return messages;
    }

    public Iterable<Message> getMessages() {
        return getMessages(null);
    }

    public void updateMessage(
            User currentUser,
            Message message,
            String text,
            String tag,
            MultipartFile file
    ) throws IOException {

        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) { message.setText(text); }
            if (!StringUtils.isEmpty(tag)) { message.setTag(tag); }
            saveFile(message, file);

            messageRepository.save(message);
        }
    }

    public void saveFile(Message message, MultipartFile file) throws IOException {
        if (
                file != null &&
                file.getOriginalFilename() != null &&
                !file.getOriginalFilename().isEmpty()
        ) {
            File uploadDir = new File((uploadPath));
            if (!uploadDir.exists()) { uploadDir.mkdir(); }
            String resultFilename = UUID.randomUUID() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
