package com.raj.quiz_app_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Column(length = 2000)
    private String description;

    // The ID of the user who created this quiz (foreign key reference)
    private String createdBy;

    // Tags like "Java", "Spring Boot", "React"
    private String tags;

    // If true, quiz has negative marking
    private boolean negativeMarkingEnabled;

    // Time limit for quiz in seconds
    private int timeLimit;

    // Difficulty level (EASY, MEDIUM, HARD)
    private String difficultyLevel;

    // Marks per question or per difficulty weight
    private double marksPerQuestion;

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    // Optional: store average score to improve dashboard performance
    private Double averageScore = 0.0;

    // Optional: store total attempts for analytics
    private int totalAttempts = 0;
}
