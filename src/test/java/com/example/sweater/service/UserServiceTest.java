package com.example.sweater.service;

import com.example.sweater.model.Role;
import com.example.sweater.model.User;
import com.example.sweater.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreateUser() {
        User user = new User();

        user.setEmail("some@mail.ru");

        boolean isUserCreated = userService.createUser(user);

        Assertions.assertTrue(isUserCreated);
        Assertions.assertNotNull(user.getActivationCode());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)), "");

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    void shouldFailUserCreation() {
        User user = new User();

        user.setUsername("John");

        Mockito.doReturn(Optional.of(new User()))
                .when(userRepository)
                .findByUsernameIgnoreCase("John");

        boolean isUserCreated = userService.createUser(user);

        Assertions.assertFalse(isUserCreated);

        Mockito.verify(userRepository, Mockito.times(0)).save(user);
        Mockito.verify(mailSender, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    void shouldActivateUser() {
        User user = new User();

        Mockito.doReturn(user)
                .when(userRepository)
                .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("activate");

        Assertions.assertTrue(isUserActivated);
        Assertions.assertNull(user.getActivationCode());

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }

    @Test
    void shouldFailUserActivation() {
        User user = new User();

        Mockito.doReturn(user)
                .when(userRepository)
                .findByActivationCode(null);

        boolean isUserActivated = userService.activateUser("activate");

        Assertions.assertFalse(isUserActivated);
        Assertions.assertNull(user.getActivationCode());

        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));

    }
}