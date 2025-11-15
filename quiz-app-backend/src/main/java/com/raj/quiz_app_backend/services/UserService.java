package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.Role;
import com.raj.quiz_app_backend.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {


     // Finds existing Google user by email or creates a new one.
    User findOrCreateGoogleUser(String email, String name);
     // Fetch a user by ID.
    User getById(String id);
     // Fetch a user by email.
    User getByEmail(String email);
     // Fetch all users (Admin only).
    List<User> getAllUsers();
     // Delete user by ID (Admin only).
    void deleteUser(String userId);
     // Update basic profile fields such as username, bio, and avatar.
    User updateProfile(String userId, String username, String bio, String avatarUrl);
     // Check if user email is verified.
    boolean isEmailVerified(String userId);
     // Change user role (Admin only).
    User changeUserRole(String userId, Role newRole);
     // Add XP or points to a user (after quiz attempts).
    User addXP(String userId, int points);
     // Get the top N users by XP for global leaderboard.
    List<User> getTopUsersByXP(int limit);
     // Deactivate (soft-delete) a user account.
    void deactivateUser(String userId);

    void reactivateUser(String userId);
     // Increment number of quizzes attempted by a user (for stats tracking).
    void incrementAttemptCount(String userId);
     // Get total quizzes attempted by user.
    int getAttemptCount(String userId);

    User updateAvatar(String id, MultipartFile file);
}
