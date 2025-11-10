package com.raj.quiz_app_backend.security;

import com.raj.quiz_app_backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// Adapter exposing our User entity to Spring Security
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user; // underlying domain user

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map role to a GrantedAuthority
        return java.util.List.of(() -> "ROLE_" + user.getRole().name());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId(); // JWT subject will be userId
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return user.isEnabled(); }

    // Expose underlying domain user
    public User getUser() { return user; }
}