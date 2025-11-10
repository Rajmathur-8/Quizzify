package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.MarkingConfig;
import com.raj.quiz_app_backend.model.DifficultyLevel;
import com.raj.quiz_app_backend.model.QuestionType;
import com.raj.quiz_app_backend.repository.MarkingConfigRepository;
import com.raj.quiz_app_backend.services.MarkingConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MarkingConfigServiceImpl implements MarkingConfigService {

    private final MarkingConfigRepository markingConfigRepo;

    // Get multiplier by difficulty & type
    @Override
    public double getMultiplier(DifficultyLevel difficulty, QuestionType type) {
        return markingConfigRepo.findByDifficultyAndType(difficulty, type)
                .map(MarkingConfig::getMultiplier)
                .orElseGet(() -> getDefaultMultiplier(difficulty, type));
    }

    // Save or update config
    @Override
    public MarkingConfig saveOrUpdate(MarkingConfig cfg) {
        Optional<MarkingConfig> existing = markingConfigRepo.findByDifficultyAndType(cfg.getDifficulty(), cfg.getType());
        if (existing.isPresent()) {
            MarkingConfig toUpdate = existing.get();
            toUpdate.setMultiplier(cfg.getMultiplier());
            return markingConfigRepo.save(toUpdate);
        }
        return markingConfigRepo.save(cfg);
    }

    // Get all configs
    @Override
    public List<MarkingConfig> getAllConfigs() {
        return markingConfigRepo.findAll();
    }

    // Delete config
    @Override
    public void deleteConfig(String id) {
        markingConfigRepo.deleteById(id);
    }

    // Get config by difficulty/type
    @Override
    public MarkingConfig getByDifficultyAndType(DifficultyLevel difficulty, QuestionType type) {
        return markingConfigRepo.findByDifficultyAndType(difficulty, type).orElse(null);
    }

    // Reset all configs to defaults
    @Override
    public void resetToDefault() {
        markingConfigRepo.deleteAll();
        List<MarkingConfig> defaults = List.of(
                new MarkingConfig(null, DifficultyLevel.EASY, QuestionType.MCQ, 1.0),
                new MarkingConfig(null, DifficultyLevel.MEDIUM, QuestionType.MCQ, 2.0),
                new MarkingConfig(null, DifficultyLevel.HARD, QuestionType.MCQ, 3.0),
                new MarkingConfig(null, DifficultyLevel.MEDIUM, QuestionType.TRUE_FALSE, 1.5),
                new MarkingConfig(null, DifficultyLevel.HARD, QuestionType.FILL_IN_THE_BLANK, 2.5)
        );
        markingConfigRepo.saveAll(defaults);
    }

    // Default multipliers (used if config missing)
    private double getDefaultMultiplier(DifficultyLevel difficulty, QuestionType type) {
        return switch (difficulty) {
            case EASY -> 1.0;
            case MEDIUM -> 2.0;
            case HARD -> 3.0;
        };
    }
}
