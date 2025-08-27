package com.tutorial.ia.controller;

import com.tutorial.ia.domain.QuestionAnswer;
import com.tutorial.ia.application.AgentService;
import com.tutorial.ia.domain.exceptions.AgentServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping("/askAgent")
    public QuestionAnswer askAgent(@RequestBody QuestionAnswer questionAnswer) {
        return agentService.askAgent(questionAnswer);
    }
}
