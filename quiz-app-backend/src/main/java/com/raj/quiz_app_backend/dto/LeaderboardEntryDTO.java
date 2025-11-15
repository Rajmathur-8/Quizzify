package com.raj.quiz_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a leaderboard entry for a user in a quiz.
 * Can be constructed using different parameter sets for convenience.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardEntryDTO {

    private String userId;
    private String username;
    private double score;
    private int rank;

    // Convenience constructor: (username, score)
    public LeaderboardEntryDTO(String username, Double score) {
        this.username = username;
        this.score = score != null ? score : 0.0;
    }

    // Convenience constructor: (username, score, rank)
    public LeaderboardEntryDTO(String username, Double score, Long rank) {
        this.username = username;
        this.score = score != null ? score : 0.0;
        this.rank = rank != null ? rank.intValue() : -1;
    }
}
