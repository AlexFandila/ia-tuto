package com.tutorial.ia.application.repository;

import com.tutorial.ia.domain.QuestionAnswer;

public interface AgentRepository {
    public QuestionAnswer sendQuestion(QuestionAnswer question);
}
