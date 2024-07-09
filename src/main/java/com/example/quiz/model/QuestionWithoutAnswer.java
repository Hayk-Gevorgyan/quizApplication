package com.example.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWithoutAnswer {
    private Integer id;
    private String text;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionWithoutAnswer(Question question) {
        this.id = question.getId();
        this.text = question.getText();
        this.option1 = question.getOption1();
        this.option2 = question.getOption2();
        this.option3 = question.getOption3();
        this.option4 = question.getOption4();
    }
}
