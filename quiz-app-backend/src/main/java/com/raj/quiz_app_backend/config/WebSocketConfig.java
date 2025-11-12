package com.raj.quiz_app_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // register STOMP endpoint for clients to connect to
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")                     // endpoint to connect to (ws or wss)
                .setAllowedOriginPatterns("*")         // adjust for production to your frontend origin
                .withSockJS();                         // SockJS fallback support
    }

    // configure simple broker and application destination prefixes
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // broker destinations (server -> client)
        config.setApplicationDestinationPrefixes("/app"); // client -> server messages prefix
        config.setUserDestinationPrefix("/user"); // for user-specific messages
    }
}
