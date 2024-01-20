package com.soumya.questionservice.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionForUser {

    private Integer id;

    private String questionTitle;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

}
