package com.raj.quiz_app_backend.services.admin;

import com.raj.quiz_app_backend.model.Quiz;
import com.raj.quiz_app_backend.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminQuizServiceImpl implements AdminQuizService {

    private final QuizRepository quizRepo;

    @Override
    public List<Quiz> getAllQuizzes() { return quizRepo.findAll(); }

    @Override
    public List<Quiz> searchQuizzes(String keyword) {
        return quizRepo.findAll().stream()
                .filter(q -> q.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || (q.getTags() != null && q.getTags().toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteQuiz(String quizId) { quizRepo.deleteById(quizId); }

    @Override
    public long countTotalQuizzes() { return quizRepo.count(); }

    @Override
    public List<Quiz> getQuizzesByCreator(String creatorId) {
        return quizRepo.findByCreatedBy(creatorId);
    }
}