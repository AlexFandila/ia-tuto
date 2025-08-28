package com.tutorial.ia.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class JournalEntry {
    private Long id;
    private String title;
    private String entry;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public JournalEntry(String title, String entry, User user) {
        this.title = title;
        this.entry = entry;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void setEntry(String entry) {
        this.entry = entry;
        this.updatedAt = LocalDateTime.now();
    }
}