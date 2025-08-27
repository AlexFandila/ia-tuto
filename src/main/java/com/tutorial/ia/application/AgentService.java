package com.tutorial.ia.application;

import com.tutorial.ia.application.repository.AgentRepository;
import com.tutorial.ia.domain.QuestionAnswer;
import com.tutorial.ia.domain.entities.QuestionAnswerEntry;
import com.tutorial.ia.domain.exceptions.AgentServiceException;
import com.tutorial.ia.infrastructure.repository.QuestionAnswerEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private QuestionAnswerEntryRepository questionAnswerEntryRepository;

    public QuestionAnswer askAgent(QuestionAnswer question) {
        try {
            // 1. Obtener respuesta del agente
            QuestionAnswer response = agentRepository.sendQuestion(question);

            // 2. Convertir el objeto a nuestra entidad de persistencia
            QuestionAnswerEntry entry = new QuestionAnswerEntry(response.getQuestion(), response.getAnswer());

            // 3. Guardar entry
            questionAnswerEntryRepository.save(entry);

            return response;
        } catch (AgentServiceException e) {
            throw new AgentServiceException("No se pudo comunicar con el agente de IA", e);
        }
    }

}
