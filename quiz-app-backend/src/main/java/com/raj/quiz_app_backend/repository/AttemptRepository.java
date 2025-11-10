package com.raj.quiz_app_backend.repository;

import com.raj.quiz_app_backend.model.Attempt;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, String> {

    long countByQuizId(String quizId);

    long countByUserId(String userId);

    // 🧮 Average score across all attempts (system-wide)
    @Query("SELECT AVG(a.score) FROM Attempt a")
    Double getAverageScore();

    // 🧠 Average score by quiz
    @Query("SELECT AVG(a.score) FROM Attempt a WHERE a.quizId = :quizId")
    Double getAverageScoreByQuizId(@Param("quizId") String quizId);

    // 🧠 Average score by user
    @Query("SELECT AVG(a.score) FROM Attempt a WHERE a.userId = :userId")
    Double getAverageScoreByUserId(@Param("userId") String userId);

    // 🧠 Average attempts per user
    @Query("SELECT COUNT(a) * 1.0 / COUNT(DISTINCT a.userId) FROM Attempt a")
    Double getAverageAttemptsPerUser();

    // 🧠 Count of distinct quizzes attempted by user
    @Query("SELECT COUNT(DISTINCT a.quizId) FROM Attempt a WHERE a.userId = :userId")
    Long getDistinctQuizCountByUser(@Param("userId") String userId);

    // 🧠 Attempts per quiz for chart (quizId -> attempt count)
    @Query("SELECT a.quizId, COUNT(a) FROM Attempt a GROUP BY a.quizId")
    List<Object[]> getAttemptsPerQuizStats();

    // 🧠 Top scoring user (for leaderboard or admin insights)
    @Query("SELECT a.userId, AVG(a.score) AS avgScore FROM Attempt a GROUP BY a.userId ORDER BY avgScore DESC LIMIT 1")
    List<Object[]> findTopScoringUser();

    // 🕒 Average completion time (assuming `duration` field in Attempt)
    @Query("SELECT AVG(a.duration) FROM Attempt a WHERE a.duration IS NOT NULL")
    Double getAverageCompletionTime();

    // 🧠 Find top scorer for a specific quiz
    @Query("SELECT a.userId, MAX(a.score) FROM Attempt a WHERE a.quizId = :quizId GROUP BY a.userId ORDER BY MAX(a.score) DESC LIMIT 1")
    List<Object[]> findTopScorerByQuiz(@Param("quizId") String quizId);
}
