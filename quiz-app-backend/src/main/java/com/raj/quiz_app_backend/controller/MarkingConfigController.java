package com.raj.quiz_app_backend.controller;

import com.raj.quiz_app_backend.model.MarkingConfig;
import com.raj.quiz_app_backend.model.DifficultyLevel;
import com.raj.quiz_app_backend.model.QuestionType;
import com.raj.quiz_app_backend.services.MarkingConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marking-config")
@RequiredArgsConstructor
public class MarkingConfigController {

    private final MarkingConfigService markingConfigService;

    // Get all marking configurations
    @GetMapping
    public ResponseEntity<List<MarkingConfig>> getAllConfigs() {
        return ResponseEntity.ok(markingConfigService.getAllConfigs());
    }

    // Get multiplier for given difficulty and type
    @GetMapping("/multiplier")
    public ResponseEntity<Double> getMultiplier(
            @RequestParam DifficultyLevel difficulty,
            @RequestParam QuestionType type
    ) {
        return ResponseEntity.ok(markingConfigService.getMultiplier(difficulty, type));
    }

    // Save or update marking config
    @PostMapping
    public ResponseEntity<MarkingConfig> saveOrUpdate(@RequestBody MarkingConfig config) {
        return ResponseEntity.ok(markingConfigService.saveOrUpdate(config));
    }

    // Delete marking config
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable String id) {
        markingConfigService.deleteConfig(id);
        return ResponseEntity.noContent().build();
    }

    // Reset to default multipliers
    @PostMapping("/reset")
    public ResponseEntity<String> resetDefaults() {
        markingConfigService.resetToDefault();
        return ResponseEntity.ok("Marking configuration reset to defaults");
    }
}
