package com.example.quiz.conroller;

import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import com.example.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("quiz")
public class QuizController {

    final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("create")
    public ResponseEntity<Integer> createQuiz(@RequestBody(required = false) Quiz quiz) {
        if (quiz == null) {
            return quizService.createQuiz();
        }
        return quizService.createQuiz(quiz);
    }

    @GetMapping("get/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable(name = "quizId") Integer quizId) {
        return quizService.getQuizById(quizId);
    }

    @PutMapping("update")
    public ResponseEntity<Integer> updateQuiz(@RequestBody Quiz quiz) {
        return quizService.updateQuiz(quiz);
    }

    @DeleteMapping("delete/{quizId}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable(name = "quizId") Integer quizId) {
        return quizService.deleteQuiz(quizId);
    }

    @PutMapping("addQuestion/{quizId}")
    public ResponseEntity<Quiz> addQuestion(@PathVariable(name = "quizId") Integer quizId, @RequestParam Integer questionId) {
        return quizService.addQuestion(quizId, questionId);
    }

    @PutMapping("deleteQuestion/{quizId}")
    public ResponseEntity<Quiz> deleteQuestion(@PathVariable(name = "quizId") Integer quizId, @RequestParam Integer questionId) {
        return quizService.deleteQuestion(quizId, questionId);
    }


}
