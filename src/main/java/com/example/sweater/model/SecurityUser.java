package com.example.sweater.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Objects;

public record SecurityUser(User user) implements UserDetails {

    @Serial
    private static final long serialVersionUID = 8514957138524205734L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles();
    }

    @Override
    public String getUsername() {
        return user != null ? user.getUsername() : "<none>";
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    public boolean isAdmin() {
        return user.isAdmin();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SecurityUser) obj;
        return Objects.equals(this.user, that.user);
    }

    @Override
    public String toString() {
        return "SecurityUser[" +
                "user=" + user + ']';
    }

}
