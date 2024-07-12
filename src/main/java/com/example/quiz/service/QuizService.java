package com.example.quiz.service;

import com.example.quiz.model.Question;
import com.example.quiz.model.QuestionResponse;
import com.example.quiz.model.QuestionWithoutAnswer;
import com.example.quiz.model.Quiz;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ResponseEntity<List<QuestionWithoutAnswer>> getQuizById(Integer id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);

        return getListResponseEntityIfPresent(optionalQuiz);
    }


    public ResponseEntity<Integer> createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
        return new ResponseEntity<>(quiz.getId(),HttpStatus.CREATED);
    }

    public ResponseEntity<Integer> updateQuiz(Quiz quiz) {
        if (!quizRepository.existsById(quiz.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        quizRepository.save(quiz);
        return new ResponseEntity<>(quiz.getId(),HttpStatus.ACCEPTED);
    }

    public ResponseEntity<List<QuestionWithoutAnswer>> deleteQuiz(Integer id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            ResponseEntity<List<QuestionWithoutAnswer>> listResponseEntity = new ResponseEntity<>(convertToQuestionsWithoutAnswer(quiz.getQuestions()), HttpStatus.OK);
            quizRepository.delete(quiz);
            return listResponseEntity;
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addQuestion(Integer quizId, Integer questionId) {

        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);

        if (optionalQuiz.isEmpty()) {
            return new ResponseEntity<>("No Quiz With Id " + quizId, HttpStatus.NOT_FOUND);
        }

        Quiz quiz = optionalQuiz.get();

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if (optionalQuestion.isEmpty()) {
            return new ResponseEntity<>("No Question With Id " + questionId, HttpStatus.NOT_FOUND);
        }

        quiz.getQuestions().add(optionalQuestion.get());

        quizRepository.save(quiz);

        return new ResponseEntity<>("Question " + questionId + " Added To " + quizId + " Quiz ", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteQuestion(Integer quizId, Integer questionId) {

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        if (optionalQuiz.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        optionalQuiz.get().getQuestions().remove(optionalQuestion.get());

        return new ResponseEntity<>(" Question " + questionId + " Removed ", HttpStatus.OK);
    }

    private List<QuestionWithoutAnswer> convertToQuestionsWithoutAnswer(Collection<Question> questions) {
        List<QuestionWithoutAnswer> questionWithoutAnswerList = new ArrayList<>();
        for (Question question : questions) {
            questionWithoutAnswerList.add(new QuestionWithoutAnswer(question));
        }
        return questionWithoutAnswerList;
    }

    public String getQuizTitleById(Integer quizId) {
        return quizRepository.getQuizTitleById(quizId);
    }

    public ResponseEntity<List<QuestionWithoutAnswer>> getQuizByTitle(String title) {
        Optional<Quiz> optionalQuiz = quizRepository.findByTitle(title);

        return getListResponseEntityIfPresent(optionalQuiz);
    }

    private static ResponseEntity<List<QuestionWithoutAnswer>> getListResponseEntityIfPresent(Optional<Quiz> optionalQuiz) {
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            List<QuestionWithoutAnswer> questionsWithoutAnswer = new ArrayList<>();
            for (Question question : quiz.getQuestions()) {
                questionsWithoutAnswer.add(new QuestionWithoutAnswer(question));
            }
            return new ResponseEntity<>(questionsWithoutAnswer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> solveQuiz(Integer quizId, List<QuestionResponse> answersList) {
        Set<QuestionResponse> answers = new HashSet<>(answersList);
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        Quiz quiz = null;
        if (optionalQuiz.isPresent()) {
            quiz = optionalQuiz.get();
        }
        if (quiz == null) {
            return new ResponseEntity<>("No Quiz Found By Id " + quizId, HttpStatus.NOT_FOUND);
        }
        if (quiz.getQuestions().size() != answers.size()) {
            return new ResponseEntity<>("Questions Count And Answers Count Dont Match", HttpStatus.BAD_REQUEST);
        } else {
            int correctCount = 0;
            for (QuestionResponse answer : answers) {
                for (var question : quiz.getQuestions()) {
                    if (question.getId().equals(answer.getId()) && question.getAnswer().equals(answer.getAnswer())) {
                        correctCount++;
                        break;
                    }
                }
            }
            String scoreString = "You Got " + correctCount + "/" + answers.size() + " Right Answers";
            return new ResponseEntity<>(scoreString, HttpStatus.OK);
        }
    }
}
