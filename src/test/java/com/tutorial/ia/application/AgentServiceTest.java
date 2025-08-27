package com.tutorial.ia.application;

import com.tutorial.ia.application.repository.AgentRepository;
import com.tutorial.ia.domain.QuestionAnswer;
import com.tutorial.ia.domain.exceptions.AgentServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AgentServiceTest {

    @Autowired
    private AgentService agentService;
    @MockitoBean
    private AgentRepository agentRepository;

    @Test
    void testAskAgent() {
        // 1. Arrange
        // Create the test data we will use
        QuestionAnswer questionAnswer = new QuestionAnswer("Pregunta de prueba", "");

        // Le decimos a mockito qued debe devolver nuestro objeto simulado
        when(agentRepository.sendQuestion(any(QuestionAnswer.class))).thenReturn(new QuestionAnswer("Pregunta de prueba", "Respuesta de prueba"));

        // 2. Act
        // Call the methos we want to test
        QuestionAnswer resp = agentService.askAgent(questionAnswer);


        // 3. Assert
        // Check the result of the test
        assert(resp.getAnswer().equals("Respuesta de prueba"));
    }

    @Test
    void testAskAgentError() {
        // 1. Arrange
        QuestionAnswer questionAnswer = new QuestionAnswer("Error", "");

        when(agentRepository.sendQuestion(any(QuestionAnswer.class))).thenThrow(new AgentServiceException("Error en la llamada a la API"));

        assertThrows(AgentServiceException.class, () -> {
            agentService.askAgent(questionAnswer);
        });
    }

}
