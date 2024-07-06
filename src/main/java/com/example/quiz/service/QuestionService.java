package com.example.quiz.service;

import com.example.quiz.model.Question;
import com.example.quiz.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Integer> createQuestion(Question question) {
        try {
            questionRepository.save(question);
            return new ResponseEntity<>(question.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Integer>> createQuestions(List<Question> questions) {

        List<Integer> ids = new ArrayList<>();
        HttpStatus status;
        try {
            questionRepository.saveAll(questions).forEach(question -> ids.add(question.getId()));
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ids, status);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        if (!questionRepository.existsById(question.getId())) {
            return new ResponseEntity<>("No Such Question With Provided ID", HttpStatus.BAD_REQUEST);
        }
        try {
            questionRepository.save(question);
            return new ResponseEntity<>("question updated", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateQuestionById(Integer id, Question question) {
        if (!questionRepository.existsById(id)) {
            return new ResponseEntity<>("No Such Question With Provided ID", HttpStatus.BAD_REQUEST);
        }
        question.setId(id);
        return updateQuestion(question);
    }

    public ResponseEntity<String> deleteQuestion(Question question) {
        questionRepository.delete(question);
        return new ResponseEntity<>("question deleted", HttpStatus.OK);
    }

    public ResponseEntity<Question> deleteQuestionById(Integer id) {
        if (!questionRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Question question = questionRepository.findById(id).get();
        questionRepository.deleteById(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    public ResponseEntity<Question> getQuestionById(Integer id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        return optionalQuestion.map(question -> new ResponseEntity<>(question, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteAllQuestions() {
        questionRepository.deleteAll();
        return new ResponseEntity<>("All Questions Deleted", HttpStatus.OK);
    }
}
