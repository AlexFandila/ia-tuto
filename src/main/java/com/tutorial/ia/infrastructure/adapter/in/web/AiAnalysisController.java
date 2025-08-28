package com.tutorial.ia.infrastructure.adapter.in.web;

import com.tutorial.ia.domain.port.in.AiAnalysisUseCase;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.AskQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiAnalysisController {

    private final AiAnalysisUseCase aiAnalysisUseCase;

    @GetMapping("/analyze/journal-entry/{journalEntryId}")
    public ResponseEntity<String> analyzeJournalEntry(@PathVariable Long journalEntryId) {
        try {
            String analysis = aiAnalysisUseCase.analyzeJournalEntry(journalEntryId);
            return ResponseEntity.ok(analysis);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/insights/user/{userId}")
    public ResponseEntity<String> generateUserInsights(@PathVariable Long userId) {
        try {
            String insights = aiAnalysisUseCase.generateUserInsights(userId);
            return ResponseEntity.ok(insights);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody AskQuestionRequest request) {
        try {
            String answer = aiAnalysisUseCase.askQuestion(request.getQuestion(), request.getUserId());
            return ResponseEntity.ok(answer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}