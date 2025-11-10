package com.raj.quiz_app_backend.repository;

import com.raj.quiz_app_backend.model.Quiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {

    // Find quizzes by creator
    List<Quiz> findByCreatedBy(String createdBy);

    // Count quizzes grouped by tag
    @Query("SELECT q.tags, COUNT(q) FROM Quiz q GROUP BY q.tags")
    List<Object[]> getQuizzesByTagCount();

    // 🧠 Find quiz with the highest average score (using Pageable to get top 1)
    @Query("SELECT q.title, AVG(a.score) as avgScore " +
            "FROM Attempt a JOIN Quiz q ON a.quizId = q.id " +
            "GROUP BY q.id, q.title " +
            "ORDER BY avgScore DESC")
    List<Object[]> findQuizWithHighestAverage(Pageable pageable);
}
