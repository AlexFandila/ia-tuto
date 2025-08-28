package com.tutorial.ia.infrastructure.adapter.out.ai;

import com.tutorial.ia.domain.port.out.AiServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAiServiceAdapter implements AiServicePort {

    private final ChatClient chatClient;

    @Override
    public String generateResponse(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public String analyzeJournalEntry(String entry) {
        String analysisPrompt = "Please analyze the following journal entry and provide insights about the author's emotional state, themes, and any patterns you notice:\n\n" + entry;
        
        return chatClient.prompt()
                .user(analysisPrompt)
                .call()
                .content();
    }

    @Override
    public String generateInsights(String userJournalHistory) {
        String insightsPrompt = "Based on the following collection of journal entries, please provide comprehensive insights about patterns, emotional trends, personal growth, and recommendations:\n\n" + userJournalHistory;
        
        return chatClient.prompt()
                .user(insightsPrompt)
                .call()
                .content();
    }
}