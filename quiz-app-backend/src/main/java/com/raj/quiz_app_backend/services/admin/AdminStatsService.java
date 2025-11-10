package com.raj.quiz_app_backend.services.admin;

import java.util.Map;

public interface AdminStatsService {
    Map<String, Object> getSystemStats(); // system-level stats
    Map<String, Object> getUserActivityStats(String userId); // stats for user
    Map<String, Object> getQuizPerformanceStats(String quizId); // stats for quiz
}
