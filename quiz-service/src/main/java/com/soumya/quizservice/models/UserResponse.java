package com.soumya.quizservice.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Integer id;

    @NotEmpty(message = "Response should not be null or empty")
    private String response;

}
