package com.tutorial.ia.application.service;

import com.tutorial.ia.domain.exception.JournalEntryNotFoundException;
import com.tutorial.ia.domain.exception.UserNotFoundException;
import com.tutorial.ia.domain.model.Goal;
import com.tutorial.ia.domain.model.JournalEntry;
import com.tutorial.ia.domain.port.in.AiAnalysisUseCase;
import com.tutorial.ia.domain.port.out.AiServicePort;
import com.tutorial.ia.domain.port.out.GoalRepositoryPort;
import com.tutorial.ia.domain.port.out.JournalEntryRepositoryPort;
import com.tutorial.ia.domain.port.out.UserRepositoryPort;
import com.tutorial.ia.infrastructure.service.PromptTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiAnalysisService implements AiAnalysisUseCase {

    private final AiServicePort aiServicePort;
    private final JournalEntryRepositoryPort journalEntryRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final GoalRepositoryPort goalRepositoryPort;
    private final PromptTemplateService promptTemplateService;

    @Override
    public String analyzeJournalEntry(Long journalEntryId) {
        JournalEntry journalEntry = journalEntryRepositoryPort.findById(journalEntryId)
                .orElseThrow(() -> new JournalEntryNotFoundException(journalEntryId));
        
        // Obtener objetivos del usuario para análisis contextualizado
        List<Goal> userGoals = goalRepositoryPort.findByUserId(journalEntry.getUser().getId());
        
        if (userGoals.isEmpty()) {
            // Sin objetivos, usar análisis básico
            return aiServicePort.analyzeJournalEntry(journalEntry.getEntry());
        }
        
        // Análisis enriquecido con objetivos
        String goalsContext = buildGoalsContext(userGoals);
        String enhancedPrompt = buildEnhancedAnalysisPrompt(journalEntry.getEntry(), goalsContext);
        
        return aiServicePort.generateResponse(enhancedPrompt);
    }

    @Override
    public String generateUserInsights(Long userId) {
        userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        
        List<JournalEntry> userEntries = journalEntryRepositoryPort.findByUserId(userId);
        List<Goal> userGoals = goalRepositoryPort.findByUserId(userId);
        
        String combinedEntries = userEntries.stream()
                .map(JournalEntry::getEntry)
                .collect(Collectors.joining("\n\n"));
        
        if (userGoals.isEmpty()) {
            // Sin objetivos, usar análisis básico
            return aiServicePort.generateInsights(combinedEntries);
        }
        
        // Análisis enriquecido con objetivos
        String goalsContext = buildGoalsContext(userGoals);
        String enhancedPrompt = buildInsightsPrompt(combinedEntries, goalsContext);
        
        return aiServicePort.generateResponse(enhancedPrompt);
    }

    @Override
    public String askQuestion(String question, Long userId) {
        userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        
        List<JournalEntry> userEntries = journalEntryRepositoryPort.findByUserId(userId);
        String context = userEntries.stream()
                .map(entry -> "Title: " + entry.getTitle() + "\nEntry: " + entry.getEntry())
                .collect(Collectors.joining("\n\n"));
        
        Map<String, Object> variables = Map.of(
            "context", context,
            "question", question
        );
        
        return aiServicePort.generateResponse(
            promptTemplateService.loadTemplate("question/context-based-question", variables)
        );
    }
    
    private String buildGoalsContext(List<Goal> goals) {
        if (goals.isEmpty()) {
            return "";
        }
        
        StringBuilder context = new StringBuilder("OBJETIVOS DEL USUARIO:\n");
        
        // Agrupar por tipo de objetivo
        List<Goal> shortTerm = goals.stream().filter(g -> g.getGoalType().name().contains("CORTO")).toList();
        List<Goal> mediumTerm = goals.stream().filter(g -> g.getGoalType().name().contains("MEDIO")).toList();
        List<Goal> longTerm = goals.stream().filter(g -> g.getGoalType().name().contains("LARGO")).toList();
        
        if (!shortTerm.isEmpty()) {
            context.append("\nOBJETIVOS A CORTO PLAZO:\n");
            shortTerm.forEach(goal -> context.append("- ").append(goal.getTitle())
                    .append(": ").append(goal.getDescription()).append("\n"));
        }
        
        if (!mediumTerm.isEmpty()) {
            context.append("\nOBJETIVOS A MEDIO PLAZO:\n");
            mediumTerm.forEach(goal -> context.append("- ").append(goal.getTitle())
                    .append(": ").append(goal.getDescription()).append("\n"));
        }
        
        if (!longTerm.isEmpty()) {
            context.append("\nOBJETIVOS A LARGO PLAZO:\n");
            longTerm.forEach(goal -> context.append("- ").append(goal.getTitle())
                    .append(": ").append(goal.getDescription()).append("\n"));
        }
        
        return context.toString();
    }
    
    private String buildEnhancedAnalysisPrompt(String journalEntry, String goalsContext) {
        Map<String, Object> variables = Map.of(
                "journalEntry", journalEntry,
                "goalsContext", goalsContext
        );

        return promptTemplateService.loadTemplate("analysis/enhanced-analysis", variables);
    }
    
    private String buildInsightsPrompt(String journalEntries, String goalsContext) {
        Map<String, Object> variables = Map.of(
                "journalEntries", journalEntries,
                "goalsContext", goalsContext
        );

        return promptTemplateService.loadTemplate("insights/enhanced-insights", variables);
    }
}