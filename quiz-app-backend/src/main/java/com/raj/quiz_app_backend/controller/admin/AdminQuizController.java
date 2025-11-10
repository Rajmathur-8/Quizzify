package com.raj.quiz_app_backend.controller.admin;

import com.raj.quiz_app_backend.model.Quiz;
import com.raj.quiz_app_backend.services.admin.AdminQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/quizzes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminQuizController {

    private final AdminQuizService adminQuizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAll() {
        return ResponseEntity.ok(adminQuizService.getAllQuizzes());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Quiz>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(adminQuizService.searchQuizzes(keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        adminQuizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(adminQuizService.countTotalQuizzes());
    }

    @GetMapping("/creator/{id}")
    public ResponseEntity<List<Quiz>> byCreator(@PathVariable String id) {
        return ResponseEntity.ok(adminQuizService.getQuizzesByCreator(id));
    }
}