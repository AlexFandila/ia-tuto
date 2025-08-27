package com.tutorial.ia.infrastructure;

import com.tutorial.ia.application.repository.AgentRepository;
import com.tutorial.ia.domain.QuestionAnswer;
import com.tutorial.ia.domain.exceptions.AgentServiceException;
import org.springframework.stereotype.Component;

@Component
public class AgentClient implements AgentRepository {

    @Override
    public QuestionAnswer sendQuestion(QuestionAnswer questionAnswer) throws AgentServiceException {
        // Simular fallo
        // Si la pregunta es "Error" lanzamos excepcion
        if (questionAnswer.getQuestion().equals("Error")) {
            throw new AgentServiceException("La API del agente de IA no está disponible");
        }

        // Aquí irá la lógica para llamar a la API externa
        // Usaremos WebClient para hacer las llamadas a la API
        return new QuestionAnswer(questionAnswer.getQuestion(), "No se pudo responder");
    }
}
