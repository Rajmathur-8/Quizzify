package com.raj.quiz_app_backend.dto;

import lombok.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResponse {

    private String id;                   // quiz id
    private String title;                // quiz title
    private String description;          // quiz description
    private List<String> tags;           // tags/topics
    private boolean negativeMarkingEnabled; // if penalty is on
    private int timeLimit;               // time limit in seconds
    private String difficultyLevel;      // e.g., EASY, MEDIUM, HARD
    private double marksPerQuestion;     // marks for each question
    private String createdBy;            // user id of creator
    private Instant createdAt;           // creation timestamp
    private Instant updatedAt;           // last updated timestamp
    private double averageScore;         // average quiz score (analytics)
    private int totalAttempts;           // total attempts (analytics)
}
