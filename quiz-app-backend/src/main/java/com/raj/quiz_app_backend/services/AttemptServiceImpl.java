package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.Attempt;
import com.raj.quiz_app_backend.repository.AttemptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Service implementation for managing quiz attempts.
 * Handles starting, submitting, and retrieving attempts for users and quizzes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AttemptServiceImpl implements AttemptService {

    private final AttemptRepository attemptRepo;
    private final LeaderboardService leaderboardService;
    private final UserService userService;

    /**
     * Start a new quiz attempt for a given user.
     */
    @Override
    public Attempt startAttempt(String quizId, String userId) {
        log.debug("🎯 Starting new attempt for user {} on quiz {}", userId, quizId);

        Attempt attempt = Attempt.builder()
                .quizId(quizId)
                .userId(userId)
                .startedAt(Instant.now())
                .build();

        Attempt saved = attemptRepo.save(attempt);
        log.info("🟢 Attempt started: {}", saved.getId());
        return saved;
    }

    /**
     * Submit a quiz attempt and update score, duration, and XP.
     */
    @Override
    public Attempt submitAttempt(String attemptId, double score, long duration) {
        log.debug("📝 Submitting attempt {} with score {}", attemptId, score);

        Attempt attempt = attemptRepo.findById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Attempt not found: " + attemptId));

        attempt.setScore(score);
        attempt.setDuration(duration);
        attempt.setSubmittedAt(Instant.now());
        attemptRepo.save(attempt);

        // Update leaderboard and user XP
        leaderboardService.updateScore(attempt.getQuizId(), attempt.getUserId(), score);
        userService.addXP(attempt.getUserId(), (int) score);

        log.info("✅ Attempt submitted: {} | Score: {} | Duration: {} sec", attemptId, score, duration);
        return attempt;
    }

    /**
     * Get all attempts made by a specific user.
     */
    @Override
    public List<Attempt> getAttemptsByUser(String userId) {
        log.debug("📜 Fetching attempts for user {}", userId);
        return attemptRepo.findAll().stream()
                .filter(a -> a.getUserId().equals(userId))
                .toList();
    }

    /**
     * Get all attempts for a specific quiz.
     */
    @Override
    public List<Attempt> getAttemptsByQuiz(String quizId) {
        log.debug("📊 Fetching attempts for quiz {}", quizId);
        return attemptRepo.findAll().stream()
                .filter(a -> a.getQuizId().equals(quizId))
                .toList();
    }
}
