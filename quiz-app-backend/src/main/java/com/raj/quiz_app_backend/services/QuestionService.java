package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.model.Question;
import java.util.List;

public interface QuestionService {

    // Create a new question or update an existing one with optional auto mark calculation
    Question createOrUpdate(Question question, boolean autoCalculate);

    // Get a question by its unique ID
    Question getById(String id);

    // Get all questions belonging to a specific quiz
    List<Question> getByQuizId(String quizId);

    // Delete a question by its ID
    void deleteById(String id);

    // Get all questions in the system (Admin use only)
    List<Question> getAll();

    // Get questions filtered by difficulty level (EASY, MEDIUM, HARD)
    List<Question> getByDifficulty(String difficulty);

    // Get questions filtered by topic tag
    List<Question> getByTag(String tagName);

    // Get random questions from a quiz for randomized quiz experience
    List<Question> getRandomQuestions(String quizId, int count);

    // Update marks, negative marking, or difficulty for an existing question
    Question updateMarksAndDifficulty(String questionId, double baseMarks, double negativeMarks, String difficulty);

    // Validate a question’s options and correct answers before saving
    boolean validateQuestion(Question question);

    // Count total number of questions in a given quiz
    long countByQuizId(String quizId);
}
