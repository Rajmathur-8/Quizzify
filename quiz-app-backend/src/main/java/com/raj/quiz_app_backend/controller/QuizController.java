package com.raj.quiz_app_backend.controller;

import com.raj.quiz_app_backend.dto.QuizRequest;
import com.raj.quiz_app_backend.dto.QuizResponse;
import com.raj.quiz_app_backend.model.Quiz;
import com.raj.quiz_app_backend.services.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    // Create quiz
    @PostMapping
    public ResponseEntity<Quiz> createQuiz(
            @RequestBody QuizRequest request,
            @RequestParam String creatorId
    ) {
        return ResponseEntity.ok(quizService.createQuiz(request, creatorId));
    }

    // Update quiz
    @PutMapping("/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(
            @PathVariable String quizId,
            @RequestParam String userId,
            @RequestBody QuizRequest request
    ) {
        return ResponseEntity.ok(quizService.updateQuiz(quizId, request, userId));
    }

    // Get quiz by ID
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable String quizId) {
        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }

    // Get all quizzes
    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    // Get quizzes by tag
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<QuizResponse>> getByTag(@PathVariable String tag) {
        return ResponseEntity.ok(quizService.getQuizzesByTag(tag));
    }

    // Get quizzes by creator
    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<QuizResponse>> getByCreator(@PathVariable String creatorId) {
        return ResponseEntity.ok(quizService.getQuizzesByCreator(creatorId));
    }

    // Delete quiz
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(
            @PathVariable String quizId,
            @RequestParam String userId
    ) {
        quizService.deleteQuiz(quizId, userId);
        return ResponseEntity.noContent().build();
    }
}
