package com.raj.quiz_app_backend.services.admin;

import com.raj.quiz_app_backend.repository.AttemptRepository;
import com.raj.quiz_app_backend.repository.QuizRepository;
import com.raj.quiz_app_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminStatsServiceImpl implements AdminStatsService {

    private final UserRepository userRepo;
    private final QuizRepository quizRepo;
    private final AttemptRepository attemptRepo;

    @Override
    public Map<String, Object> getSystemStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("activeUsers", userRepo.countByEnabledTrue());
        stats.put("totalQuizzes", quizRepo.count());
        stats.put("avgAttemptsPerUser", attemptRepo.getAverageAttemptsPerUser());
        return stats;
    }

    @Override
    public Map<String, Object> getUserActivityStats(String userId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("attempts", attemptRepo.countByUserId(userId));
        stats.put("avgScore", attemptRepo.getAverageScoreByUserId(userId));
        stats.put("distinctQuizzes", attemptRepo.getDistinctQuizCountByUser(userId));
        return stats;
    }

    @Override
    public Map<String, Object> getQuizPerformanceStats(String quizId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalAttempts", attemptRepo.countByQuizId(quizId));
        stats.put("avgScore", attemptRepo.getAverageScoreByQuizId(quizId));
        stats.put("topUser", attemptRepo.findTopScorerByQuiz(quizId));
        return stats;
    }
}