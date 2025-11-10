package com.raj.quiz_app_backend.services;

import com.raj.quiz_app_backend.dto.*;
import com.raj.quiz_app_backend.model.Quiz;
import com.raj.quiz_app_backend.repository.QuizRepository;
import com.raj.quiz_app_backend.services.QuizService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepo;
    private final ModelMapper mapper;

    @Override
    public Quiz createQuiz(QuizRequest req, String creatorId) {
        Quiz quiz = mapper.map(req, Quiz.class);
        quiz.setCreatedBy(creatorId);
        return quizRepo.save(quiz);
    }

    @Override
    public Quiz updateQuiz(String quizId, QuizRequest req, String userId) {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow();
        if (!quiz.getCreatedBy().equals(userId))
            throw new SecurityException("Unauthorized to edit this quiz");

        mapper.map(req, quiz);
        return quizRepo.save(quiz);
    }

    @Override
    public void deleteQuiz(String quizId, String userId) {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow();
        if (!quiz.getCreatedBy().equals(userId))
            throw new SecurityException("Unauthorized to delete this quiz");
        quizRepo.delete(quiz);
    }

    @Override
    public QuizResponse getQuizById(String quizId) {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow();
        return mapper.map(quiz, QuizResponse.class);
    }

    @Override
    public List<QuizResponse> getAllQuizzes() {
        return quizRepo.findAll().stream()
                .map(q -> mapper.map(q, QuizResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizResponse> getQuizzesByTag(String tag) {
        return quizRepo.findAll().stream()
                .filter(q -> q.getTags() != null && q.getTags().contains(tag))
                .map(q -> mapper.map(q, QuizResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizResponse> getQuizzesByCreator(String creatorId) {
        return quizRepo.findByCreatedBy(creatorId).stream()
                .map(q -> mapper.map(q, QuizResponse.class))
                .collect(Collectors.toList());
    }
}
