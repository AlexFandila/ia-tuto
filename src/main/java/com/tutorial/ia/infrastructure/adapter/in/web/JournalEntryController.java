package com.tutorial.ia.infrastructure.adapter.in.web;

import com.tutorial.ia.domain.model.JournalEntry;
import com.tutorial.ia.domain.port.in.JournalManagementUseCase;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.CreateJournalEntryRequest;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.JournalEntryResponse;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.UpdateJournalEntryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/journal-entries")
@RequiredArgsConstructor
public class JournalEntryController {

    private final JournalManagementUseCase journalManagementUseCase;

    @PostMapping
    public ResponseEntity<JournalEntryResponse> createJournalEntry(@RequestBody CreateJournalEntryRequest request) {
        try {
            JournalEntry journalEntry = journalManagementUseCase.createJournalEntry(
                    request.getTitle(), 
                    request.getEntry(), 
                    request.getUserId()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(JournalEntryResponse.from(journalEntry));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntryResponse> getJournalEntryById(@PathVariable Long id) {
        return journalManagementUseCase.getJournalEntryById(id)
                .map(entry -> ResponseEntity.ok(JournalEntryResponse.from(entry)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JournalEntryResponse>> getUserJournalEntries(@PathVariable Long userId) {
        List<JournalEntryResponse> entries = journalManagementUseCase.getUserJournalEntries(userId).stream()
                .map(JournalEntryResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(entries);
    }

    @GetMapping
    public ResponseEntity<List<JournalEntryResponse>> getAllJournalEntries() {
        List<JournalEntryResponse> entries = journalManagementUseCase.getAllJournalEntries().stream()
                .map(JournalEntryResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(entries);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntryResponse> updateJournalEntry(@PathVariable Long id, 
                                                                   @RequestBody UpdateJournalEntryRequest request) {
        try {
            JournalEntry journalEntry = journalManagementUseCase.updateJournalEntry(
                    id, request.getTitle(), request.getEntry()
            );
            return ResponseEntity.ok(JournalEntryResponse.from(journalEntry));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournalEntry(@PathVariable Long id) {
        journalManagementUseCase.deleteJournalEntry(id);
        return ResponseEntity.noContent().build();
    }
}