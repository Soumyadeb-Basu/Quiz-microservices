package com.soumya.quizservice.feign;

import com.soumya.quizservice.models.QuestionForUser;
import com.soumya.quizservice.models.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuestionInterface {

    @GetMapping("questions/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numQ);

    @PostMapping("questions/getQuestionForUser")
    public ResponseEntity<List<QuestionForUser>> getQuestionsForUser(@RequestBody List<Integer> questionIds);

    @PostMapping("questions/getScore")
    public ResponseEntity<String> getScore(@RequestBody List<UserResponse> responses);


}
