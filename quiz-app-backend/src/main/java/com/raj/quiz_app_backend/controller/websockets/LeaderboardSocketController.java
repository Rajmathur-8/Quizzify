package com.raj.quiz_app_backend.controller.websockets;


import com.raj.quiz_app_backend.dto.LeaderboardEntryDTO;
import com.raj.quiz_app_backend.services.LeaderboardService;
import com.raj.quiz_app_backend.dto.ScoreUpdateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Receives score updates (from quiz attempt flow or clients) and broadcasts updated leaderboard snapshots.
 *
 * Incoming messages should be sent to /app/leaderboard/{quizId}/update
 * Clients subscribe to /topic/leaderboard/{quizId} to receive top-N updates.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class LeaderboardSocketController {

    private final LeaderboardService leaderboardService;
    private final SimpMessagingTemplate template;

    // Client or server can send updates here (e.g., after answer or on attempt submit)
    @MessageMapping("/leaderboard/{quizId}/update")
    public void handleScoreUpdate(@DestinationVariable String quizId,
                                  @Payload ScoreUpdateMessage msg) {
        try {
            log.debug("Received score update for quizId={} user={} score={}", quizId, msg.getUsername(), msg.getScore());

            // Update scoreboard (persist/cache in leaderboard service)
            leaderboardService.updateScore(quizId, msg.getUsername(), msg.getScore());

            // Optionally increment (if incremental)
            if (msg.getDelta() != null && msg.getDelta() != 0) {
                leaderboardService.incrementScore(quizId, msg.getUsername(), msg.getDelta());
            }

            // Fetch top N (e.g., top 10) and broadcast snapshot
            int topN = msg.getTopN() != null ? msg.getTopN() : 10;
            List<LeaderboardEntryDTO> top = leaderboardService.getTopN(quizId, topN);

            template.convertAndSend("/topic/leaderboard/" + quizId, top);

        } catch (Exception ex) {
            log.error("Error processing leaderboard update for quizId={}: {}", quizId, ex.getMessage(), ex);
        }
    }

    // Admin or server can call this to clear leaderboard for a quiz, broadcast empty list
    @MessageMapping("/leaderboard/{quizId}/clear")
    public void clearLeaderboard(@DestinationVariable String quizId) {
        leaderboardService.clear(quizId);
        template.convertAndSend("/topic/leaderboard/" + quizId, List.of());
    }
}
