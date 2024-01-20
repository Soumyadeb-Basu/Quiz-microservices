package com.soumya.quizservice.services;


import com.soumya.quizservice.exception.ResourceNotFoundException;
import com.soumya.quizservice.feign.QuestionInterface;
import com.soumya.quizservice.models.QuestionForUser;
import com.soumya.quizservice.models.Quiz;
import com.soumya.quizservice.models.UserResponse;
import com.soumya.quizservice.repositories.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuestionInterface questionInterface;


    @Autowired
    public QuizService(QuizRepository quizRepository, QuestionInterface questionInterface) {
        this.quizRepository= quizRepository;
        this.questionInterface=questionInterface;
    }

    public ResponseEntity<String> createQuiz(String category, int numberOfQuestions, String title) {

        List<Integer> questions = questionInterface.getQuestionsForQuiz(category,numberOfQuestions).getBody();

        Quiz quiz= new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);

        quizRepository.save(quiz);
        log.info("New Quiz created with id: "+ quiz.getId());

        return new ResponseEntity<>("Created Quiz!", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionForUser>> getQuestionsForUser(Integer id)throws ResourceNotFoundException {

        Quiz quiz;
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if(optionalQuiz.isPresent())
            quiz= optionalQuiz.get();
        else {
            log.error("Quiz not found....");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Quiz with the given Id not found");
        }
        List<Integer> questionIds= quiz.getQuestionIds();
        if(questionIds.isEmpty()) {
            log.error("Quiz has no questions...");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Quiz is empty without any questions");
        }
        List<QuestionForUser> questionForUsers= questionInterface.getQuestionsForUser(questionIds).getBody();
        log.info("User Question returned....");
        return new ResponseEntity<>(questionForUsers,HttpStatus.OK);

    }

    public ResponseEntity<String> calculateResult(List<UserResponse> response)throws ResourceNotFoundException {
        String finalResponse = questionInterface.getScore(response).getBody();
        log.info("Final Response returned to User....");
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }
}
