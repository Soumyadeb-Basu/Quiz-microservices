package com.soumya.quizservice.repositories;


import com.soumya.quizservice.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {
}
