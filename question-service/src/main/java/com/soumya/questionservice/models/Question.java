package com.soumya.questionservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Question Title cannot be null or blank")
    private String questionTitle;

    @NotEmpty(message = "Option 1 cannot be null or blank")
    private String option1;

    @NotEmpty(message = "Option 2 cannot be null or blank")
    private String option2;

    @NotEmpty(message = "Option 3 cannot be null or blank")
    private String option3;

    @NotEmpty(message = "Option 4 cannot be null or blank")
    private String option4;

    @NotEmpty(message = "Right Answer cannot be null or blank")
    private String rightAnswer;

    @NotEmpty(message = "Category cannot be null or blank")
    private String category;

    private String difficultyLevel;

}
