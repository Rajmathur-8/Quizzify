package com.raj.quiz_app_backend.services.admin;

import com.raj.quiz_app_backend.repository.AttemptRepository;
import com.raj.quiz_app_backend.repository.QuizRepository;
import com.raj.quiz_app_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserRepository userRepo;
    private final QuizRepository quizRepo;
    private final AttemptRepository attemptRepo;

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> map = new HashMap<>();
        map.put("totalUsers", userRepo.count());
        map.put("totalQuizzes", quizRepo.count());
        map.put("totalAttempts", attemptRepo.count());
        map.put("averageScore", attemptRepo.getAverageScore());
        return map;
    }

    @Override
    public Map<String, Object> getChartData() {
        Map<String, Object> map = new HashMap<>();
        map.put("usersByMonth", userRepo.getUserRegistrationStats());
        map.put("quizzesByTag", quizRepo.getQuizzesByTagCount());
        map.put("attemptsByQuiz", attemptRepo.getAttemptsPerQuizStats());
        return map;
    }

    @Override
    public Map<String, Object> getPerformanceSummary() {
        Map<String, Object> map = new HashMap<>();
        // ✅ Get best-performing quiz (via QuizRepository)
        var topQuiz = quizRepo.findQuizWithHighestAverage(PageRequest.of(0, 1));
        if (!topQuiz.isEmpty()) {
            Object[] data = topQuiz.get(0);
            map.put("bestQuiz", Map.of("title", data[0], "averageScore", data[1]));
        } else {
            map.put("bestQuiz", Map.of("title", "N/A", "averageScore", 0));
        }
        // ✅ Get top user
        var topUser = attemptRepo.findTopScoringUser();
        if (!topUser.isEmpty()) {
            Object[] u = topUser.get(0);
            map.put("topUser", Map.of("userId", u[0], "averageScore", u[1]));
        } else {
            map.put("topUser", "No attempts yet");
        }
        // ✅ Average completion time
        map.put("averageCompletionTime", attemptRepo.getAverageCompletionTime());
        return map;
    }

}