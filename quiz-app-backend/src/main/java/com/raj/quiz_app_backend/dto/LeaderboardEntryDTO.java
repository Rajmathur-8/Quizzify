package com.raj.quiz_app_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardEntryDTO {
    private String username; // username or display name
    private double score;    // total score
    private long rank;       // user's rank (1-indexed)

    // Convenience constructor for top lists without rank
    public LeaderboardEntryDTO(String username, Double score) {
        this.username = username;
        this.score = score != null ? score : 0;
    }
}
