package com.raj.quiz_app_backend.services.admin;


import java.util.Map;

public interface AdminDashboardService {
    Map<String, Object> getDashboardStats(); // total users, quizzes, attempts
    Map<String, Object> getChartData(); // graphs data
    Map<String, Object> getPerformanceSummary(); // best quiz, top user
}
