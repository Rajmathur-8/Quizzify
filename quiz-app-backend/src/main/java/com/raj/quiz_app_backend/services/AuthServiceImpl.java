package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.dto.AuthRequest;
import com.raj.quiz_app_backend.dto.AuthResponse;
import com.raj.quiz_app_backend.dto.RegisterRequest;
import com.raj.quiz_app_backend.model.Role;
import com.raj.quiz_app_backend.model.User;
import com.raj.quiz_app_backend.repository.UserRepository;
import com.raj.quiz_app_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Handles registration, OTP verification, login, and password reset.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder encoder;
    private final JavaMailSenderImpl mailSender;

    @Value("${app.frontend.base-url:http://localhost:5173}")
    private String frontendBaseUrl;

    // ================================================================
    // ✅ REGISTER (User)
    // ================================================================
    @Override
    public AuthResponse register(RegisterRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create new user (disabled until OTP verified)
        User user = User.builder()
                .email(req.getEmail())
                .username(req.getUsername())
                .password(encoder.encode(req.getPassword()))
                .role(Role.PLAYER)
                .enabled(false)
                .build();
        userRepo.save(user);

        // Generate OTP and send email
        String otp = otpService.generateOtp(user.getEmail());
        Map<String, String> placeholders = Map.of(
                "USERNAME", user.getUsername(),
                "OTP_CODE", otp
        );

        try {
            String html = emailService.loadTemplate("email_otp_template.html", placeholders);
            emailService.sendEmail(user.getEmail(), "Your Quizzify Verification Code", html);
            log.info("📧 OTP sent successfully to {}", user.getEmail());
        } catch (Exception e) {
            log.error("❌ Failed to send OTP email to {}: {}", user.getEmail(), e.getMessage());
        }

        return new AuthResponse(null, user.getId(), user.getEmail());
    }

    // ================================================================
    // ✅ VERIFY OTP
    // ================================================================
    @Override
    public void verifyOtp(String email, String otp) {
        log.info("🔍 Verifying OTP for {}", email);

        if (otpService.validateOtp(email, otp)) {
            User user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            user.setEnabled(true);
            userRepo.save(user);

            log.info("✅ Email verified successfully for {}", email);
        } else {
            log.warn("❌ Invalid or expired OTP for {}", email);
            throw new IllegalArgumentException("Invalid or expired OTP");
        }
    }

    // ================================================================
    // ✅ LOGIN (User)
    // ================================================================
    @Override
    public AuthResponse login(AuthRequest req) {
        log.info("🔐 Login attempt for {}", req.getEmail());

        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        if (!user.isEnabled()) {
            throw new IllegalArgumentException("Email not verified yet");
        }

        String token = jwtService.generateToken(user.getId());
        log.info("✅ Login successful for {}", user.getEmail());
        return new AuthResponse(token, user.getId(), user.getEmail());
    }

    // ================================================================
    // ✅ REGISTER ADMIN
    // ================================================================
    @Override
    public AuthResponse registerAdmin(RegisterRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Admin already exists with this email");
        }

        User admin = User.builder()
                .email(req.getEmail())
                .username(req.getUsername())
                .password(encoder.encode(req.getPassword()))
                .role(Role.ADMIN)
                .enabled(true)
                .build();

        userRepo.save(admin);
        log.info("👑 Admin registered: {}", admin.getEmail());

        String jwt = jwtService.generateToken(admin.getId());
        return new AuthResponse(jwt, admin.getId(), admin.getEmail());
    }

    // ================================================================
    // ✅ INITIATE PASSWORD RESET
    // ================================================================
    @Override
    public void initiatePasswordReset(String email) {
        log.info("🔄 Password reset requested for {}", email);

        userRepo.findByEmail(email).ifPresentOrElse(user -> {
            String token = tokenService.generatePasswordResetToken(user);
            String resetUrl = frontendBaseUrl + "/reset-password?token=" + token;

            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("USERNAME", Optional.ofNullable(user.getUsername()).orElse("User"));
            placeholders.put("ACTION_LINK", resetUrl);

            try {
                String html = emailService.loadTemplate("password_reset_template.html", placeholders);
                emailService.sendEmail(user.getEmail(), "Reset your Quizzify password", html);
                log.info("📨 Password reset email sent to {}", user.getEmail());
            } catch (Exception e) {
                log.error("❌ Failed to send password reset email: {}", e.getMessage());
            }
        }, () -> log.warn("⚠️ Password reset requested for non-existent email: {}", email));
    }

    // ================================================================
    // ✅ RESET PASSWORD
    // ================================================================
    @Override
    public void resetPassword(String token, String newPassword) {
        log.info("🔒 Resetting password using token...");

        String userId = tokenService.validatePasswordResetToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("Invalid or expired reset token");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for this token"));

        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);

        log.info("✅ Password reset successful for {}", user.getEmail());
    }
}
