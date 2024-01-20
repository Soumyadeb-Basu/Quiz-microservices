package com.soumya.questionservice.services;

import com.soumya.questionservice.exception.ResourceNotFoundException;
import com.soumya.questionservice.mapper.QuestionMapper;
import com.soumya.questionservice.models.Question;
import com.soumya.questionservice.models.QuestionForUser;
import com.soumya.questionservice.models.UserResponse;
import com.soumya.questionservice.repositories.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository= questionRepository;
    }

    public ResponseEntity<List<Question>> getAllQuestions()throws ResourceNotFoundException {
        if(questionRepository.findAll().isEmpty()) {
            log.error("No Questions found to be displayed....");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "No Questions Found to be displayed");
        }
        log.info("ALl Questions returned...");
        return ResponseEntity.ok(questionRepository.findAll());
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionRepository.save(question);
        log.info("Question added successfully....");
        return new ResponseEntity<>("Successfully Added Question!", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Question>> getByCategory(String category) {
        if(questionRepository.getByCategory(category).isEmpty()) {
            log.error("No Questions found for category: "+ category);
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"No Questions are available for the given category: "+ category);
        }
        log.info("Questions for given category returned....");
        return ResponseEntity.ok(questionRepository.getByCategory(category));
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int numberOfQuestions) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(category,numberOfQuestions);

        if(questions.isEmpty()) {
            log.error("No questions found in given category");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"No Questions are found in given category for creating quiz");
        }
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionForUser>> getQuestionsForUser(List<Integer> questionIds) {
        List<QuestionForUser> questionForUsers= new ArrayList<>();
        for(Integer id: questionIds) {
            if(questionRepository.findById(id).isPresent()) {
                QuestionForUser userQuestion = QuestionMapper.INSTANCE.questionForUser(questionRepository.findById(id).get());
                questionForUsers.add(userQuestion);
            }
            else throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Question with id "+id+" not found");
        }
        return new ResponseEntity<>(questionForUsers,HttpStatus.OK);
    }

    public ResponseEntity<String> getScore(List<UserResponse> responses) {
        int correctAnswers=0;
        int count=0;
        for(UserResponse r: responses) {
            if(r.getResponse()==null||r.getResponse().isEmpty()) {
                log.error("Empty Response Body....");
                throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Response is empty");
            }
            Question question= questionRepository.findById(r.getId()).get();
            if(r.getResponse().equals(question.getRightAnswer()))
                correctAnswers++;
        }
        String finalResponse = String.format("Correct Answers : %s out of %s" ,correctAnswers,responses.size());
        log.info("Final Response returned to User....");
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }
}
