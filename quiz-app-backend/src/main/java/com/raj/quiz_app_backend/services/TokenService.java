package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.User;

public interface TokenService {
    String generateVerificationToken(User user);
    String validateVerificationToken(String token);
    String generatePasswordResetToken(User user);
    String validatePasswordResetToken(String token);
}