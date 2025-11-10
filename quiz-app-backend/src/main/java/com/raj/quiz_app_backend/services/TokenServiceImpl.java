package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Handles token generation and validation for:
 *  ✅ Email Verification
 *  ✅ Password Reset
 * Uses separate expiration times for better security.
 * Compatible with io.jsonwebtoken v0.13.0.
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret; // shared secret base key

    @Value("${app.verification-expiration-hours:24}")
    private int verificationExpirationHours; // 24h default

    // Password reset token lifespan (15 minutes)
    private static final long PASSWORD_RESET_EXPIRATION_MS = 15 * 60 * 1000;

    private SecretKey signingKey;

    // ================================================================
    // ✅ Initialization
    // ================================================================
    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long!");
        }
        signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        log.info("✅ TokenService initialized: verification={}h, reset={}min",
                verificationExpirationHours, PASSWORD_RESET_EXPIRATION_MS / 60000);
    }

    // ================================================================
    // ✅ Email Verification Token
    // ================================================================
    @Override
    public String generateVerificationToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + verificationExpirationHours * 3600000L);

        return Jwts.builder()
                .subject(user.getId())
                .claim("email", user.getEmail())
                .claim("type", "VERIFY_EMAIL")
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey, Jwts.SIG.HS256) // ✅ type-safe algorithm
                .compact();
    }

    @Override
    public String validateVerificationToken(String token) {
        try {
            Claims claims = parseClaims(token);

            if (!"VERIFY_EMAIL".equals(claims.get("type"))) {
                throw new IllegalArgumentException("Invalid token type: not a verification token");
            }
            if (isExpired(claims)) {
                throw new IllegalArgumentException("Verification token expired");
            }

            return claims.getSubject(); // userId
        } catch (Exception e) {
            log.error("❌ Verification token validation failed: {}", e.getMessage());
            return null;
        }
    }

    // ================================================================
    // ✅ Password Reset Token
    // ================================================================
    @Override
    public String generatePasswordResetToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + PASSWORD_RESET_EXPIRATION_MS);

        return Jwts.builder()
                .subject(user.getId())
                .claim("email", user.getEmail())
                .claim("type", "RESET_PASSWORD")
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String validatePasswordResetToken(String token) {
        try {
            Claims claims = parseClaims(token);

            if (!"RESET_PASSWORD".equals(claims.get("type"))) {
                throw new IllegalArgumentException("Invalid token type: not a password reset token");
            }
            if (isExpired(claims)) {
                throw new IllegalArgumentException("Password reset token expired");
            }

            return claims.getSubject();
        } catch (Exception e) {
            log.error("❌ Password reset token validation failed: {}", e.getMessage());
            return null;
        }
    }

    // ================================================================
    // 🔐 Utility Methods
    // ================================================================
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("❌ Failed to parse token: {}", e.getMessage());
            throw new RuntimeException("Invalid token");
        }
    }

    private boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
