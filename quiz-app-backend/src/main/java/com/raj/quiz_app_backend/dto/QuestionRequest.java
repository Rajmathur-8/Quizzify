package com.raj.quiz_app_backend.dto;

import com.raj.quiz_app_backend.model.DifficultyLevel;
import com.raj.quiz_app_backend.model.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String quizId;
    private String questionText;
    private List<String> options;
    private List<String> correctAnswers;
    private QuestionType type;
    private DifficultyLevel difficulty;
    private Double baseMarks;
    private Boolean autoCalculateMarks = true;
    private Double negativeMarks;
}