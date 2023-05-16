package com.example.sweater;

import com.example.sweater.model.Role;
import com.example.sweater.model.User;
import com.example.sweater.repository.UserRepository;
import com.example.sweater.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder) {
//        return args -> {
//            User admin = new User("admin", encoder.encode("123"), Role.ADMIN);
//            admin.setActive(true);
//            User user = new User("user", encoder.encode("123"));
//            user.setActive(true);
//            users.save(admin);
//            users.save(user);
//        };
//    }
}
