package com.raj.quiz_app_backend.repository;

import com.raj.quiz_app_backend.model.DifficultyLevel;
import com.raj.quiz_app_backend.model.MarkingConfig;
import com.raj.quiz_app_backend.model.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MarkingConfigRepository extends JpaRepository<MarkingConfig, String> {
    Optional<MarkingConfig> findByDifficultyAndType(DifficultyLevel difficulty, QuestionType type);
}