package com.example.sweater.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean active;
    private String email;
    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.active = false;
        this.roles = new HashSet<>();
        this.roles.add(Role.USER);
    }

    public User(String username, String password, Role role) {
        this(username, password);
        this.addRole(role);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

}
