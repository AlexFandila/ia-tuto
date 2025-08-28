package com.tutorial.ia.domain.port.out;

public interface AiServicePort {
    String generateResponse(String prompt);
    String analyzeJournalEntry(String entry);
    String generateInsights(String userJournalHistory);
}