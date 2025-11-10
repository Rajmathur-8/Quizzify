package com.raj.quiz_app_backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final StringRedisTemplate redisTemplate;
    private final Random random = new Random();

    // Generate 6-digit OTP
    public String generateOtp(String email) {
        String otp = String.format("%06d", random.nextInt(999999));
        redisTemplate.opsForValue().set("OTP:" + email, otp, 10, TimeUnit.MINUTES);
        log.info("📩 OTP generated for {}: {}", email, otp);
        return otp;
    }

    // Validate OTP
    public boolean validateOtp(String email, String otp) {
        String stored = redisTemplate.opsForValue().get("OTP:" + email);
        if (stored != null && stored.equals(otp)) {
            redisTemplate.delete("OTP:" + email);
            return true;
        }
        return false;
    }
}
