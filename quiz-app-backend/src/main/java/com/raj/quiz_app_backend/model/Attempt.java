package com.raj.quiz_app_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String quizId;

    private String userId;

    private double score;

    private double timeTaken; // optional, if you want fine-grained tracking

    private Long duration; // store in seconds (used for average completion time)

    private Instant startedAt = Instant.now();

    private Instant submittedAt;
}