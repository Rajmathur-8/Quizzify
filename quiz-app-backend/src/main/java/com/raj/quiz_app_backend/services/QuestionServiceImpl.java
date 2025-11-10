package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.MarkingConfig;
import com.raj.quiz_app_backend.model.Question;
import com.raj.quiz_app_backend.model.DifficultyLevel;
import com.raj.quiz_app_backend.model.QuestionType;
import com.raj.quiz_app_backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepo;
    private final MarkingConfigService markingConfigService;

    // Create or update question with optional auto-calculated marks
    @Override
    public Question createOrUpdate(Question question, boolean autoCalculate) {
        if (autoCalculate) {
            double multiplier = markingConfigService.getMultiplier(
                    question.getDifficulty(),
                    question.getType()
            );
            question.setMarks(multiplier);
        }
        return questionRepo.save(question);
    }

    // Get a question by its ID
    @Override
    public Question getById(String id) {
        return questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));
    }

    // Get all questions for a quiz
    @Override
    public List<Question> getByQuizId(String quizId) {
        return questionRepo.findByQuizId(quizId);
    }

    // Delete question by ID
    @Override
    public void deleteById(String id) {
        questionRepo.deleteById(id);
    }

    // Get all questions (Admin)
    @Override
    public List<Question> getAll() {
        return questionRepo.findAll();
    }

    // Get questions filtered by difficulty
    @Override
    public List<Question> getByDifficulty(String difficulty) {
        return questionRepo.findAll().stream()
                .filter(q -> q.getDifficulty().name().equalsIgnoreCase(difficulty))
                .collect(Collectors.toList());
    }

    // Get questions filtered by tag
    @Override
    public List<Question> getByTag(String tagName) {
        return questionRepo.findAll().stream()
                .filter(q -> q.getTags() != null && q.getTags().toLowerCase().contains(tagName.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Randomize questions for a quiz
    @Override
    public List<Question> getRandomQuestions(String quizId, int count) {
        List<Question> allQuestions = questionRepo.findByQuizId(quizId);
        Collections.shuffle(allQuestions);
        return allQuestions.stream().limit(count).collect(Collectors.toList());
    }

    // Update marks, difficulty, or negative marking
    @Override
    public Question updateMarksAndDifficulty(String questionId, double baseMarks, double negativeMarks, String difficulty) {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        question.setMarks(baseMarks);
        question.setNegativeMarks(negativeMarks);
        question.setDifficulty(DifficultyLevel.valueOf(difficulty.toUpperCase()));
        return questionRepo.save(question);
    }

    // Validate question structure and answers
    @Override
    public boolean validateQuestion(Question question) {
        if (question.getQuestionText() == null || question.getQuestionText().isBlank())
            return false;
        if (question.getOptions() == null || question.getOptions().isEmpty())
            return false;
        if (question.getCorrectAnswers() == null || question.getCorrectAnswers().isEmpty())
            return false;

        // Ensure all correct answers are valid options
        for (String answer : question.getCorrectAnswers()) {
            if (!question.getOptions().contains(answer)) {
                return false;
            }
        }
        return true;
    }

    // Count total questions in a quiz
    @Override
    public long countByQuizId(String quizId) {
        return questionRepo.countByQuizId(quizId);
    }
}
