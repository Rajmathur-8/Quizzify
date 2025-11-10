package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.dto.AttemptRequest;
import com.raj.quiz_app_backend.model.Attempt;

import java.util.List;

public interface AttemptService {
    Attempt startAttempt(String quizId, String userId);
    Attempt submitAttempt(String attemptId, double score, long duration);
    List<Attempt> getAttemptsByUser(String userId);
    List<Attempt> getAttemptsByQuiz(String quizId);
}