package com.raj.quiz_app_backend.security;

import com.raj.quiz_app_backend.model.User;
import com.raj.quiz_app_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

// Loads UserDetails from DB by id or email
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // injected repository

    @Override
    public UserDetails loadUserByUsername(String usernameOrId) throws UsernameNotFoundException {
        // Try find by id first, then by email
        com.raj.quiz_app_backend.model.User user = userRepository.findById(usernameOrId)
                .orElseGet(() -> userRepository.findByEmail(usernameOrId)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrId)));
        return new UserDetailsImpl(user);
    }

    // Convenience method to load directly by id
    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
        return new UserDetailsImpl(user);
    }
}
