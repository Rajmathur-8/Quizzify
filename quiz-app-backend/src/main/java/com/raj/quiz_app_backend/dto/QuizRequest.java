package com.raj.quiz_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizRequest {

    private String title;                // quiz title
    private String description;          // quiz description
    private List<String> tags;           // topic tags
    private boolean negativeMarkingEnabled; // enable/disable negative marking
    private int timeLimit;               // time limit in seconds
    private String difficultyLevel;      // e.g., EASY, MEDIUM, HARD
    private double marksPerQuestion;     // base marks per question
}