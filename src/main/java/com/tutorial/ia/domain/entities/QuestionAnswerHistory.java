package com.tutorial.ia.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "question_answer_history")
public class QuestionAnswerHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
