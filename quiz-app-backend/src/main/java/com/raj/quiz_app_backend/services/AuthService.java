package com.raj.quiz_app_backend.services;


import com.raj.quiz_app_backend.dto.AuthRequest;
import com.raj.quiz_app_backend.dto.AuthResponse;
import com.raj.quiz_app_backend.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest req);
    AuthResponse login(AuthRequest req);
    void verifyOtp(String email, String otp);
    void initiatePasswordReset(String email);
    void resetPassword(String token, String newPassword);
    AuthResponse registerAdmin(RegisterRequest req);
}