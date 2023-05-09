package com.example.sweater.config;

import com.example.sweater.constant.UrlPath;
import com.example.sweater.service.JpaUserDetailsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JpaUserDetailsManager jpaUserDetailsManager;


    public WebSecurityConfig(JpaUserDetailsManager jpaUserDetailsManager) {
        this.jpaUserDetailsManager = jpaUserDetailsManager;
    }


    @Bean
    @Order(2)
    SecurityFilterChain h2ConsoleSecurityFilterChain (HttpSecurity http) throws Exception {
        return http
                .securityMatcher(AntPathRequestMatcher.antMatcher(UrlPath.H2_CONSOLE))
                .authorizeHttpRequests(auth -> auth.requestMatchers(AntPathRequestMatcher.antMatcher(UrlPath.H2_CONSOLE)).permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher(UrlPath.H2_CONSOLE)))
                .headers(headers -> headers.frameOptions().disable())
                .build();
    }

    @Bean
    @Order(3)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                            auth.requestMatchers("/").permitAll();
                            auth.requestMatchers("/error").permitAll();
                            auth.requestMatchers(UrlPath.REGISTRATION).permitAll();
                            auth.requestMatchers("/login").permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .formLogin()
                .loginPage("/login")
                .and()
                .rememberMe()
                .and()
                .logout()
                .permitAll()
                .and()
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
