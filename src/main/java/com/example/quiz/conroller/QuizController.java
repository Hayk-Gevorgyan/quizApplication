package com.example.quiz.conroller;

import com.example.quiz.model.QuestionWithoutAnswer;
import com.example.quiz.model.Quiz;
import com.example.quiz.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("get")
    public ResponseEntity<List<QuestionWithoutAnswer>>
    getQuizById(@RequestParam(name = "quizId", required = false) Integer quizId,
                @RequestParam(name = "title", required = false) String title) {
        if (quizId != null && (title == null || quizService.getQuizTitleById(quizId).equals(title))) {
            return quizService.getQuizById(quizId);
        }
        if (title != null) {
            return quizService.getQuizByTitle(title);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("update")
    public ResponseEntity<Integer> updateQuiz(@RequestBody Quiz quiz) {
        return quizService.updateQuiz(quiz);
    }

    @DeleteMapping("delete/{quizId}")
    public ResponseEntity<List<QuestionWithoutAnswer>> deleteQuiz(@PathVariable(name = "quizId") Integer quizId) {
        return quizService.deleteQuiz(quizId);
    }

    @PutMapping("addQuestion/{quizId}")
    public ResponseEntity<String> addQuestion(@PathVariable(name = "quizId") Integer quizId, @RequestParam(name = "questionId") Integer questionId) {
        return quizService.addQuestion(quizId, questionId);
    }

    @PutMapping("deleteQuestion/{quizId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable(name = "quizId") Integer quizId, @RequestParam(name = "questionId") Integer questionId) {
        return quizService.deleteQuestion(quizId, questionId);
    }


}
