package com.tutorial.ia.domain.port.in;

public interface AiAnalysisUseCase {
    String analyzeJournalEntry(Long journalEntryId);
    String generateUserInsights(Long userId);
    String askQuestion(String question, Long userId);
}