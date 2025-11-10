package com.raj.quiz_app_backend.controller;

import com.raj.quiz_app_backend.dto.LeaderboardEntryDTO;
import com.raj.quiz_app_backend.services.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    // Get top N users for a quiz
    @GetMapping("/{quizId}/top")
    public ResponseEntity<List<LeaderboardEntryDTO>> getTopN(
            @PathVariable String quizId,
            @RequestParam(defaultValue = "10") int n) {
        return ResponseEntity.ok(leaderboardService.getTopN(quizId, n));
    }

    // Get user rank and score in quiz leaderboard
    @GetMapping("/{quizId}/user/{username}")
    public ResponseEntity<LeaderboardEntryDTO> getUserRank(
            @PathVariable String quizId,
            @PathVariable String username) {
        return ResponseEntity.ok(leaderboardService.getUserRank(quizId, username));
    }

    // Get all entries (admin use)
    @GetMapping("/{quizId}/all")
    public ResponseEntity<List<LeaderboardEntryDTO>> getAll(@PathVariable String quizId) {
        return ResponseEntity.ok(leaderboardService.getAll(quizId));
    }

    // Update or increment score (used internally after quiz submission)
    @PostMapping("/{quizId}/update")
    public ResponseEntity<Void> updateScore(
            @PathVariable String quizId,
            @RequestParam String username,
            @RequestParam double score) {
        leaderboardService.updateScore(quizId, username, score);
        return ResponseEntity.ok().build();
    }

    // Clear a leaderboard (Admin only)
    @DeleteMapping("/{quizId}/clear")
    public ResponseEntity<Void> clearLeaderboard(@PathVariable String quizId) {
        leaderboardService.clear(quizId);
        return ResponseEntity.noContent().build();
    }

    // Get total participants
    @GetMapping("/{quizId}/participants")
    public ResponseEntity<Long> getTotalParticipants(@PathVariable String quizId) {
        return ResponseEntity.ok(leaderboardService.getTotalParticipants(quizId));
    }
}
