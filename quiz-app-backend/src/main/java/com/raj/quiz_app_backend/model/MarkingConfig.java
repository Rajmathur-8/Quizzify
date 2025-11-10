package com.raj.quiz_app_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marking_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarkingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private double multiplier;
}
