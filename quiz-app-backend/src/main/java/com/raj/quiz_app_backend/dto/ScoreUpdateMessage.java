package com.raj.quiz_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload sent from client or server to update leaderboard.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreUpdateMessage {
    private String username;    // username or userId depending on your leaderboard service
    private Double score;       // absolute score (optional)
    private Double delta;       // incremental change (optional)
    private Integer topN;       // how many top entries to return (optional)
}
