package com.soumya.quizservice.exception;


import com.soumya.quizservice.models.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidArgumentException(MethodArgumentNotValidException ex) {

        Map<String,String> errors= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                error->
                {   String fieldName= ((FieldError)error).getField();
                    String defaultMsg= error.getDefaultMessage();
                    errors.put(fieldName, defaultMsg);
                }
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

        APIResponse response= new APIResponse(ex.getErrorCode(),ex.getErrorMessage());
        return new ResponseEntity<>(response,ex.getErrorCode());
    }

}
