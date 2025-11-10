package com.raj.quiz_app_backend.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AttemptRequest {
    private String quizId;
    // Map questionId -> list of selected answers
    private Map<String, List<String>> answers;
    private long timeTakenMs;
}
