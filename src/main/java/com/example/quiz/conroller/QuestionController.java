package com.example.quiz.conroller;

import com.example.quiz.model.Question;
import com.example.quiz.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping("create")
    public ResponseEntity<Integer> createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @PostMapping("createMultiple")
    public ResponseEntity<List<Integer>> createQuestions(@RequestBody List<Question> questions) {
        return questionService.createQuestions(questions);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestionById(@PathVariable Integer id, @RequestBody Question question) {
        return questionService.updateQuestionById(id, question);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteQuestion(@RequestBody Question question) {
        return questionService.deleteQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Question> deleteQuestionById(@PathVariable(name = "id") Integer id) {
        return questionService.deleteQuestionById(id);
    }

    @DeleteMapping("deleteAll")
    public ResponseEntity<String> deleteAllQuestions() {
        return questionService.deleteAllQuestions();
    }
}
