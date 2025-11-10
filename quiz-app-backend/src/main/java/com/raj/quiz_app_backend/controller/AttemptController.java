package com.raj.quiz_app_backend.controller;

import com.raj.quiz_app_backend.model.Attempt;
import com.raj.quiz_app_backend.services.AttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptService attemptService;

    // Start a new quiz attempt
    @PostMapping("/start")
    public ResponseEntity<Attempt> startAttempt(
            @RequestParam String quizId,
            @RequestParam String userId
    ) {
        Attempt attempt = attemptService.startAttempt(quizId, userId);
        return ResponseEntity.ok(attempt);
    }

    // Submit a quiz attempt
    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<Attempt> submitAttempt(
            @PathVariable String attemptId,
            @RequestParam double score,
            @RequestParam long duration
    ) {
        Attempt attempt = attemptService.submitAttempt(attemptId, score, duration);
        return ResponseEntity.ok(attempt);
    }

    // Get all attempts by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Attempt>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(attemptService.getAttemptsByUser(userId));
    }

    // Get all attempts for a quiz
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Attempt>> getByQuiz(@PathVariable String quizId) {
        return ResponseEntity.ok(attemptService.getAttemptsByQuiz(quizId));
    }
}
