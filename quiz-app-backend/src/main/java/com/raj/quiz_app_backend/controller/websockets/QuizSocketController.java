package com.raj.quiz_app_backend.controller.websockets;


import com.raj.quiz_app_backend.dto.QuizEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Handles real-time quiz room events:
 * - user join/leave notifications
 * - answer events (for UI to show live activity)
 * - generic quiz events
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class QuizSocketController {

    private final SimpMessagingTemplate template;

    /**
     * A participant sends a message to /app/quiz/{quizId}/event
     * Server rebroadcasts to /topic/quiz/{quizId}/events so all subscribers see it.
     */
    @MessageMapping("/quiz/{quizId}/event")
    public void handleQuizEvent(@DestinationVariable String quizId,
                                @Payload QuizEventMessage message,
                                @Header("simpSessionId") String sessionId) {
        log.debug("Quiz event for quizId={} from session={} : {}", quizId, sessionId, message);

        // Broadcast the event to all clients subscribed to this quiz room
        template.convertAndSend("/topic/quiz/" + quizId + "/events", message);
    }

    /**
     * Optional: Send a private message to a single user (example).
     * Client can subscribe to /user/queue/quiz/{quizId}/private
     */
    @MessageMapping("/quiz/{quizId}/private")
    public void handlePrivate(@DestinationVariable String quizId,
                              @Payload QuizEventMessage message,
                              @Header("simpUser") org.springframework.security.core.Authentication principal) {
        if (principal != null && principal.getName() != null) {
            String username = principal.getName();
            log.debug("Private message for user={} in quiz={}", username, quizId);
            template.convertAndSendToUser(username, "/queue/quiz/" + quizId + "/private", message);
        }
    }
}
