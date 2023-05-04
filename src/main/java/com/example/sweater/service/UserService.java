package com.example.sweater.service;

import com.example.sweater.model.Role;
import com.example.sweater.model.User;
import com.example.sweater.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public boolean createUser(User user) {
        String username = user.getUsername();
        if (userRepository.findByUsernameIgnoreCase(username).isPresent()) return false;
        user.setActive(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.getRoles().add(Role.USER);
        log.info("Saving new User {}", username);
        return true;
    }
}
