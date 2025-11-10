package com.raj.quiz_app_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String quizId;

    @Column(length = 1000)
    private String questionText;

    @ElementCollection
    private List<String> options;

    @ElementCollection
    private List<String> correctAnswers;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    @Enumerated(EnumType.STRING)
    private QuestionType type; // e.g. MCQ, TRUE_FALSE, FILL_IN_BLANK

    private double marks;

    private double negativeMarks;

    private String tags; // e.g., "Java,Spring Boot"

    private boolean active = true;
}
