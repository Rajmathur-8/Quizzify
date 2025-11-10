package com.raj.quiz_app_backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

// Interceptor to validate JWT token during WebSocket handshake
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        // Extract token from query param 'token' first, then from Authorization header
        String token = null;
        String query = request.getURI().getQuery();
        if (query != null && query.contains("token=")) {
            for (String p : query.split("&")) {
                if (p.startsWith("token=")) { token = p.substring(6); break; }
            }
        }
        if (token == null && request instanceof ServletServerHttpRequest servletReq) {
            String header = servletReq.getServletRequest().getHeader(SecurityConstants.AUTH_HEADER);
            if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                token = header.substring(SecurityConstants.TOKEN_PREFIX.length());
            }
        }

        if (token == null) return false;
        try {
            String userId = jwtService.extractUserId(token);
            return userId != null && !jwtService.isTokenExpired(token);
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) { }
}
