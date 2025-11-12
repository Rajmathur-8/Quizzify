package com.raj.quiz_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic quiz room event sent to clients:
 * "JOIN", "LEAVE", "ANSWER_SUBMITTED", "TIMER_TICK", etc.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizEventMessage {
    private String type;          // event type
    private String username;      // user who caused event
    private String payload;       // optional JSON/string payload (e.g., choice id)
    private Long timestamp;       // server/client timestamp
}
