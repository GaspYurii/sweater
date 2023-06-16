package com.example.sweater.service;

import com.example.sweater.model.Message;
import com.example.sweater.model.User;
import com.example.sweater.model.dto.MessageDto;
import com.example.sweater.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<MessageDto> getMessages(String filter, Pageable pageable, User user) {

        if (filter != null && !filter.isEmpty()) {
            return messageRepository.findByTag(filter, pageable, user);
        } else {
            return messageRepository.findAll(pageable, user);
        }
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

    public Iterable<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Page<MessageDto> getMessagesOfUser(Pageable pageable, User user) {
        return messageRepository.findAllByUser(pageable, user);
    }
}
