package com.raj.quiz_app_backend.repository;

import com.raj.quiz_app_backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findByQuizId(String quizId);
    long countByQuizId(String quizId);
}
