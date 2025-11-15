package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.Role;
import com.raj.quiz_app_backend.model.User;
import com.raj.quiz_app_backend.repository.UserRepository;
import com.raj.quiz_app_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    @Override
    public User updateAvatar(String id, MultipartFile file) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        final String folder = "uploads/avatars";
        try {
            // ensure directory exists
            Path uploadDir = Paths.get(folder);
            Files.createDirectories(uploadDir);

            // sanitize original filename and avoid path traversal
            String original = Optional.ofNullable(file.getOriginalFilename()).orElse("avatar");
            String baseName = Paths.get(original).getFileName().toString(); // removes any path segments
            String safeBase = baseName.replaceAll("[^a-zA-Z0-9._-]", "_"); // keep it conservative
            String filename = id + "_" + System.currentTimeMillis() + "_" + safeBase;

            Path dest = uploadDir.resolve(filename);

            // write file to disk (safer than transferTo in some environments)
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, dest, StandardCopyOption.REPLACE_EXISTING);
            }

            String avatarUrl = "/" + folder + "/" + filename; // e.g. /uploads/avatars/...
            user.setAvatarUrl(avatarUrl);

            User saved = userRepo.save(user);

            // log success (use your logger)
            // logger.info("Saved avatar for user {} at {}", id, dest.toAbsolutePath());
            System.out.printf("Saved avatar for user %s at %s%n", id, dest.toAbsolutePath().toString());

            return saved;
        } catch (Exception e) {
            // log details for debugging
            System.err.printf("Failed to upload avatar for user %s: %s%n", id, e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to upload avatar: " + e.getMessage(), e);
        }
    }
}
