package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.Role;
import com.raj.quiz_app_backend.model.User;
import com.raj.quiz_app_backend.repository.UserRepository;
import com.raj.quiz_app_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    // Create user if Google login or return existing
    @Override
    public User findOrCreateGoogleUser(String email, String name) {
        return userRepo.findByEmail(email).orElseGet(() -> {
            User user = User.builder()
                    .email(email)
                    .username(name)
                    .role(Role.PLAYER)
                    .enabled(true)
                    .xp(0)
                    .quizzesAttempted(0)
                    .build();
            return userRepo.save(user);
        });
    }

    // Get user by ID
    @Override
    public User getById(String id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get user by email
    @Override
    public User getByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get all users
    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Delete user
    @Override
    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
    }

    // Update user profile
    @Override
    public User updateProfile(String userId, String username, String bio, String avatarUrl) {
        User user = getById(userId);
        user.setUsername(username);
        if (bio != null) user.setBio(bio);
        if (avatarUrl != null) user.setAvatarUrl(avatarUrl);
        return userRepo.save(user);
    }

    // Check email verification status
    @Override
    public boolean isEmailVerified(String userId) {
        return getById(userId).isEnabled();
    }

    // Change user role (Admin use)
    @Override
    public User changeUserRole(String userId, Role newRole) {
        User user = getById(userId);
        user.setRole(newRole);
        return userRepo.save(user);
    }

    // Add XP after completing quiz
    @Override
    public User addXP(String userId, int points) {
        User user = getById(userId);
        user.setXp(user.getXp() + points);
        return userRepo.save(user);
    }

    // Get top users by XP
    @Override
    public List<User> getTopUsersByXP(int limit) {
        return userRepo.findAll().stream()
                .sorted(Comparator.comparingInt(User::getXp).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    // Deactivate account
    @Override
    public void deactivateUser(String userId) {
        User user = getById(userId);
        user.setEnabled(false);
        userRepo.save(user);
    }

    // Reactivate account
    @Override
    public void reactivateUser(String userId) {
        User user = getById(userId);
        user.setEnabled(true);
        userRepo.save(user);
    }

    // Increment total attempts
    @Override
    public void incrementAttemptCount(String userId) {
        User user = getById(userId);
        user.setQuizzesAttempted(user.getQuizzesAttempted() + 1);
        userRepo.save(user);
    }

    // Get total attempts count
    @Override
    public int getAttemptCount(String userId) {
        return getById(userId).getQuizzesAttempted();
    }
}
