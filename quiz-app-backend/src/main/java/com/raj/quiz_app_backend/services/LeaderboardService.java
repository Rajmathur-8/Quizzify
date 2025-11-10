package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.dto.LeaderboardEntryDTO;
import java.util.List;

public interface LeaderboardService {

    // Add or update a user's score for a quiz leaderboard
    void updateScore(String quizId, String username, double score);

    // Increment a user's score by a specific amount
    void incrementScore(String quizId, String username, double delta);

    // Get top N users from the leaderboard of a quiz
    List<LeaderboardEntryDTO> getTopN(String quizId, int n);

    // Get the rank and score of a specific user in a quiz
    LeaderboardEntryDTO getUserRank(String quizId, String username);

    // Get all entries from the leaderboard (for admin use)
    List<LeaderboardEntryDTO> getAll(String quizId);

    // Clear all scores for a quiz leaderboard
    void clear(String quizId);

    // Get total number of participants in a quiz leaderboard
    long getTotalParticipants(String quizId);
}
