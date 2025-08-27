package com.tutorial.ia.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "question_answer_entry")
public class QuestionAnswerEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private String answer;

    public QuestionAnswerEntry(String question, String answer) {
        this.answer = answer;
        this.question = question;
    }

    public QuestionAnswerEntry() {
        super();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
