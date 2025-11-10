package com.raj.quiz_app_backend.controller;

import com.raj.quiz_app_backend.dto.*;
import com.raj.quiz_app_backend.model.User;
import com.raj.quiz_app_backend.security.UserDetailsImpl;
import com.raj.quiz_app_backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> payload) {
        authService.verifyOtp(payload.get("email"), payload.get("otp"));
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.registerAdmin(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/login-admin")
    public ResponseEntity<AuthResponse> loginAdmin(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.login(req)); // same login logic, but only for admins
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        authService.initiatePasswordReset(email);
        return ResponseEntity.ok("Password reset link sent to email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> payload) {
        authService.resetPassword(payload.get("token"), payload.get("newPassword"));
        return ResponseEntity.ok("Password reset successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication auth) {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        return ResponseEntity.ok(user.getUser());
    }
}
