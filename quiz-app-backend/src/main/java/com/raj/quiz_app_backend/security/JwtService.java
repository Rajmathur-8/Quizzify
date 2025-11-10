package com.raj.quiz_app_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * Handles JWT generation, validation, and extraction logic.
 * Compatible with JJWT v0.13.0 and Spring Boot 3.5.x
 */
@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:3600000}") // default 1 hour
    private long jwtExpirationMs;

    // ✅ SecretKey (JJWT 0.13.0 requires javax.crypto.SecretKey for HMAC)
    private SecretKey getSigningKey() {
        // ensure secret is at least 32 bytes long for HS256
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ Generate a JWT using user ID as subject
    public String generateToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey()) // default is HS256 if key is HMAC
                .compact();
    }

    // ✅ Extract userId (subject)
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Extract a specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // ✅ Parse all claims (JJWT 0.13.0 syntax)
    private Claims extractAllClaims(String token) {
        return Jwts.parser()  // ✅ use new static parser()
                .verifyWith(getSigningKey()) // verifies signature
                .build()
                .parseSignedClaims(token) // parse and verify
                .getPayload();
    }

    // ✅ Validate if token is valid for a given user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String userId = extractUserId(token);
            return userId.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    // ✅ Check if the token has expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
