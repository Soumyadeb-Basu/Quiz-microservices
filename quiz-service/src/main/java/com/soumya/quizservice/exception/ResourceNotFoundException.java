package com.soumya.quizservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    private final HttpStatus errorCode;

    private final String errorMessage;

}
