package com.raj.quiz_app_backend.controller;

import com.raj.quiz_app_backend.model.Role;
import com.raj.quiz_app_backend.model.User;
import com.raj.quiz_app_backend.security.UserDetailsImpl;
import com.raj.quiz_app_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    // Get all users (admin)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Update profile
    @PutMapping("/{id}/profile")
    public ResponseEntity<User> updateProfile(
            @PathVariable String id,
            @RequestBody Map<String, String> updateFields
    ) {
        String username = updateFields.get("username");
        String bio = updateFields.get("bio");
        String avatarUrl = updateFields.get("avatarUrl");
        return ResponseEntity.ok(userService.updateProfile(id, username, bio, avatarUrl));
    }

    // Add XP to user
    @PostMapping("/{id}/xp")
    public ResponseEntity<User> addXP(
            @PathVariable String id,
            @RequestParam int points
    ) {
        return ResponseEntity.ok(userService.addXP(id, points));
    }

    // Get top users by XP
    @GetMapping("/top")
    public ResponseEntity<List<User>> getTopUsers(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(userService.getTopUsersByXP(limit));
    }

    // Activate or deactivate user
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> setUserStatus(
            @PathVariable String id,
            @RequestParam boolean active
    ) {
        if (active) userService.reactivateUser(id);
        else userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }

    // Get total attempts
    @GetMapping("/{id}/attempts")
    public ResponseEntity<Integer> getAttemptCount(@PathVariable String id) {
        return ResponseEntity.ok(userService.getAttemptCount(id));
    }

//    @GetMapping("/me")
//    public ResponseEntity<User> getCurrentUser(Authentication auth) {
//        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
//        return ResponseEntity.ok(user.getUser());
//    }
}
