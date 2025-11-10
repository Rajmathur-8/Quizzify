package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.dto.QuizRequest;
import com.raj.quiz_app_backend.dto.QuizResponse;
import com.raj.quiz_app_backend.model.Quiz;
import java.util.List;

public interface QuizService {
    Quiz createQuiz(QuizRequest req, String creatorId); // create quiz
    Quiz updateQuiz(String quizId, QuizRequest req, String userId); // edit quiz
    void deleteQuiz(String quizId, String userId); // delete quiz
    QuizResponse getQuizById(String quizId); // fetch one
    List<QuizResponse> getAllQuizzes(); // all quizzes
    List<QuizResponse> getQuizzesByTag(String tag); // filter by tag
    List<QuizResponse> getQuizzesByCreator(String creatorId); // own quizzes
}
