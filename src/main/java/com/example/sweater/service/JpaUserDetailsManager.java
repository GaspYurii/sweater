package com.example.sweater.service;

import com.example.sweater.model.SecurityUser;
import com.example.sweater.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsManager implements UserDetailsService {

    private final UserRepository users;

    public JpaUserDetailsManager(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users
                .findByUsernameIgnoreCase(username)
                .map(SecurityUser::new)
                .orElseThrow( () -> new UsernameNotFoundException("Username not found: " + username));
    }
}
