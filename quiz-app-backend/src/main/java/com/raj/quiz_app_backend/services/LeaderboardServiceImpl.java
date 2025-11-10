package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.dto.LeaderboardEntryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final StringRedisTemplate redisTemplate;

    private String getKey(String quizId) {
        return "leaderboard:" + quizId;
    }

    // Add or update a user's score directly (set absolute score)
    @Override
    public void updateScore(String quizId, String username, double score) {
        redisTemplate.opsForZSet().add(getKey(quizId), username, score);
    }

    // Increment a user's score by delta
    @Override
    public void incrementScore(String quizId, String username, double delta) {
        redisTemplate.opsForZSet().incrementScore(getKey(quizId), username, delta);
    }

    // Get top N entries sorted by score descending
    @Override
    public List<LeaderboardEntryDTO> getTopN(String quizId, int n) {
        Set<ZSetOperations.TypedTuple<String>> top =
                redisTemplate.opsForZSet().reverseRangeWithScores(getKey(quizId), 0, n - 1);

        if (top == null) return List.of();

        return top.stream()
                .map(entry -> new LeaderboardEntryDTO(entry.getValue(), entry.getScore()))
                .collect(Collectors.toList());
    }

    // Get user's rank and score
    @Override
    public LeaderboardEntryDTO getUserRank(String quizId, String username) {
        Long rank = redisTemplate.opsForZSet().reverseRank(getKey(quizId), username);
        Double score = redisTemplate.opsForZSet().score(getKey(quizId), username);

        if (rank == null || score == null)
            return new LeaderboardEntryDTO(username, 0, -1); // not found

        // Convert 0-based Redis rank to 1-based human rank
        return new LeaderboardEntryDTO(username, score, rank + 1);
    }

    // Get all leaderboard entries (sorted)
    @Override
    public List<LeaderboardEntryDTO> getAll(String quizId) {
        Set<ZSetOperations.TypedTuple<String>> entries =
                redisTemplate.opsForZSet().reverseRangeWithScores(getKey(quizId), 0, -1);

        if (entries == null) return List.of();

        return entries.stream()
                .map(entry -> new LeaderboardEntryDTO(entry.getValue(), entry.getScore()))
                .collect(Collectors.toList());
    }

    // Clear leaderboard for quiz
    @Override
    public void clear(String quizId) {
        redisTemplate.delete(getKey(quizId));
    }

    // Get total number of participants
    @Override
    public long getTotalParticipants(String quizId) {
        Long count = redisTemplate.opsForZSet().zCard(getKey(quizId));
        return count != null ? count : 0;
    }
}
