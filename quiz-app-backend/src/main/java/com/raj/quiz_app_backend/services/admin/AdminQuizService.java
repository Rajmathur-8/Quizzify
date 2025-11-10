package com.raj.quiz_app_backend.services.admin;

import com.raj.quiz_app_backend.model.Quiz;
import java.util.List;

public interface AdminQuizService {
    List<Quiz> getAllQuizzes(); // all quizzes
    List<Quiz> searchQuizzes(String keyword); // search by tag/title
    void deleteQuiz(String quizId); // delete quiz
    long countTotalQuizzes(); // total quizzes
    List<Quiz> getQuizzesByCreator(String creatorId); // quizzes by creator
}