package com.raj.quiz_app_backend.controller;

import com.raj.quiz_app_backend.model.Question;
import com.raj.quiz_app_backend.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // Create or update a question
    @PostMapping
    public ResponseEntity<Question> createOrUpdate(
            @RequestBody Question question,
            @RequestParam(defaultValue = "true") boolean autoCalculate
    ) {
        if (!questionService.validateQuestion(question))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(questionService.createOrUpdate(question, autoCalculate));
    }

    // Get question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getById(@PathVariable String id) {
        return ResponseEntity.ok(questionService.getById(id));
    }

    // Get all questions (Admin use)
    @GetMapping
    public ResponseEntity<List<Question>> getAll() {
        return ResponseEntity.ok(questionService.getAll());
    }

    // Get questions by quiz ID
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Question>> getByQuiz(@PathVariable String quizId) {
        return ResponseEntity.ok(questionService.getByQuizId(quizId));
    }

    // Get questions by difficulty
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<Question>> getByDifficulty(@PathVariable String difficulty) {
        return ResponseEntity.ok(questionService.getByDifficulty(difficulty));
    }

    // Get questions by tag
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<Question>> getByTag(@PathVariable String tag) {
        return ResponseEntity.ok(questionService.getByTag(tag));
    }

    // Get random questions
    @GetMapping("/quiz/{quizId}/random")
    public ResponseEntity<List<Question>> getRandomQuestions(
            @PathVariable String quizId,
            @RequestParam(defaultValue = "5") int count
    ) {
        return ResponseEntity.ok(questionService.getRandomQuestions(quizId, count));
    }

    // Update marks or difficulty
    @PutMapping("/{id}/update")
    public ResponseEntity<Question> updateMarksAndDifficulty(
            @PathVariable String id,
            @RequestParam double baseMarks,
            @RequestParam double negativeMarks,
            @RequestParam String difficulty
    ) {
        return ResponseEntity.ok(questionService.updateMarksAndDifficulty(id, baseMarks, negativeMarks, difficulty));
    }

    // Delete question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        questionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Count questions per quiz
    @GetMapping("/quiz/{quizId}/count")
    public ResponseEntity<Long> countByQuiz(@PathVariable String quizId) {
        return ResponseEntity.ok(questionService.countByQuizId(quizId));
    }
}
