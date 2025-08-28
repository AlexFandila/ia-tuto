package com.tutorial.ia.application.service;

import com.tutorial.ia.domain.model.JournalEntry;
import com.tutorial.ia.domain.port.in.AiAnalysisUseCase;
import com.tutorial.ia.domain.port.out.AiServicePort;
import com.tutorial.ia.domain.port.out.JournalEntryRepositoryPort;
import com.tutorial.ia.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiAnalysisService implements AiAnalysisUseCase {

    private final AiServicePort aiServicePort;
    private final JournalEntryRepositoryPort journalEntryRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public String analyzeJournalEntry(Long journalEntryId) {
        JournalEntry journalEntry = journalEntryRepositoryPort.findById(journalEntryId)
                .orElseThrow(() -> new IllegalArgumentException("Journal entry not found"));
        
        return aiServicePort.analyzeJournalEntry(journalEntry.getEntry());
    }

    @Override
    public String generateUserInsights(Long userId) {
        userRepositoryPort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        List<JournalEntry> userEntries = journalEntryRepositoryPort.findByUserId(userId);
        String combinedEntries = userEntries.stream()
                .map(JournalEntry::getEntry)
                .collect(Collectors.joining("\n\n"));
        
        return aiServicePort.generateInsights(combinedEntries);
    }

    @Override
    public String askQuestion(String question, Long userId) {
        userRepositoryPort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        List<JournalEntry> userEntries = journalEntryRepositoryPort.findByUserId(userId);
        String context = userEntries.stream()
                .map(entry -> "Title: " + entry.getTitle() + "\nEntry: " + entry.getEntry())
                .collect(Collectors.joining("\n\n"));
        
        String prompt = "Based on the following journal entries:\n\n" + context + 
                       "\n\nPlease answer this question: " + question;
        
        return aiServicePort.generateResponse(prompt);
    }
}