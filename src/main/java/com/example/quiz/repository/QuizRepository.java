package com.example.quiz.repository;

import com.example.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Query(nativeQuery = true, value = "SELECT q.title FROM Quiz q WHERE q.id = :quizId")
    String getQuizTitleById(@Param("quizId") Integer quizId);

    Optional<Quiz> findByTitle(String title);
}
