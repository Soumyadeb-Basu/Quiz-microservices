package com.soumya.questionservice.controllers;


import com.soumya.questionservice.models.Question;
import com.soumya.questionservice.models.QuestionForUser;
import com.soumya.questionservice.models.UserResponse;
import com.soumya.questionservice.services.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
@Tag(name = "Question Controller", description = "Controller to manage Question service related operations")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("getAllQuestions")
    @Operation(summary = "Gets all Questions", description = "Gets all the questions for User")
    @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Question.class))})
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("getByCategory/{category}")
    @Operation(summary = "Gets questions by category", description = "Gets the questions for User on basis of category passed")
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Question.class))})
    @ApiResponse(responseCode = "404", description = "Not Found",content = @Content)
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getByCategory(category);
    }

    @PostMapping("addQuestion")
    @Operation(summary = "Add Questions", description = "Adds a new question for User")
    @ApiResponse(responseCode = "201", description = "Created", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    public ResponseEntity<String> addQuestion(@Valid @RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,@RequestParam int numQ) {
        return questionService.getQuestionsForQuiz(category,numQ);
    }

    @PostMapping("getQuestionForUser")
    public ResponseEntity<List<QuestionForUser>> getQuestionsForUser(@RequestBody List<Integer> questionIds) {
        return questionService.getQuestionsForUser(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<String> getScore(@RequestBody List<UserResponse> responses) {
        return questionService.getScore(responses);
    }
}
