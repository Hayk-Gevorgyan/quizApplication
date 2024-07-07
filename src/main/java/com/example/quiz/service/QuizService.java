package com.example.quiz.service;

import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizService {
    final QuizRepository quizRepository;
    final QuestionRepository questionRepository;

    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public ResponseEntity<Integer> createQuiz() {
        Quiz quiz = new Quiz();
        quizRepository.save(quiz);
        return new ResponseEntity<>(quiz.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<Quiz> getQuizById(Integer id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        return optionalQuiz.map(quiz -> new ResponseEntity<>(quiz, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<Integer> createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
        return new ResponseEntity<>(quiz.getId(),HttpStatus.CREATED);
    }

    public ResponseEntity<Integer> updateQuiz(Quiz quiz) {
        if (quizRepository.findById(quiz.getId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        quizRepository.save(quiz);
        return new ResponseEntity<>(quiz.getId(),HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Quiz> deleteQuiz(Integer id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            quizRepository.delete(optionalQuiz.get());
            return new ResponseEntity<>(optionalQuiz.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Quiz> addQuestion(Integer quizId, Integer questionId) {

        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);

        if (optionalQuiz.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Quiz quiz = optionalQuiz.get();

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if (optionalQuestion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        quiz.getQuestions().add(optionalQuestion.get());

        quizRepository.save(quiz);

        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    public ResponseEntity<Quiz> deleteQuestion(Integer quizId, Integer questionId) {

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        if (optionalQuiz.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        optionalQuiz.get().getQuestions().remove(optionalQuestion.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
