package com.tutorial.ia.infrastructure.adapter.in.web.dto;

import com.tutorial.ia.domain.model.JournalEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntryResponse {
    private Long id;
    private String title;
    private String entry;
    private Long userId;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static JournalEntryResponse from(JournalEntry journalEntry) {
        return new JournalEntryResponse(
                journalEntry.getId(),
                journalEntry.getTitle(),
                journalEntry.getEntry(),
                journalEntry.getUser() != null ? journalEntry.getUser().getId() : null,
                journalEntry.getUser() != null ? journalEntry.getUser().getUsername() : null,
                journalEntry.getCreatedAt(),
                journalEntry.getUpdatedAt()
        );
    }
}