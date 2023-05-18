package com.example.sweater.service;

import com.example.sweater.model.Role;
import com.example.sweater.model.User;
import com.example.sweater.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final MailSender mailSender;

    @Value("${hostname}")
    private String hostname;

    public UserService(UserRepository userRepository, MailSender mailSender, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.encoder = encoder;
    }

    public boolean createUser(User user) {
        String username = user.getUsername();
        if (userRepository.findByUsernameIgnoreCase(username).isPresent()) return false;

        user.setPassword(encoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        user.addRole(Role.USER);

        userRepository.save(user);
        log.info("Saving new User {}", username);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = "Hello, " + user.getUsername() + "\n" +
                    "Welcome to Sweater. Please, visit next link: " +
                    hostname + "/activate/" + user.getActivationCode();
            mailSender.send(user.getEmail(), "Activation code", message);
        }

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) return false;

        user.setActive(true);
        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }
}
