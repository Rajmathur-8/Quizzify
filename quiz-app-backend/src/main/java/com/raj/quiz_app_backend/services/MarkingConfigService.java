package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.DifficultyLevel;
import com.raj.quiz_app_backend.model.MarkingConfig;
import com.raj.quiz_app_backend.model.QuestionType;
import java.util.List;

public interface MarkingConfigService {

    // Get the multiplier value for a given difficulty and question type
    double getMultiplier(DifficultyLevel difficulty, QuestionType type);

    // Save a new marking configuration or update an existing one
    MarkingConfig saveOrUpdate(MarkingConfig cfg);

    // Get all available marking configurations
    List<MarkingConfig> getAllConfigs();

    // Delete a marking configuration by its ID
    void deleteConfig(String id);

    // Get a specific marking configuration by difficulty and question type
    MarkingConfig getByDifficultyAndType(DifficultyLevel difficulty, QuestionType type);

    // Reset all marking configurations to default multipliers
    void resetToDefault();
}
