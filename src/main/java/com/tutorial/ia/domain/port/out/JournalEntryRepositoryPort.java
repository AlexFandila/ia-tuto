package com.tutorial.ia.domain.port.out;

import com.tutorial.ia.domain.model.JournalEntry;
import com.tutorial.ia.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JournalEntryRepositoryPort {
    JournalEntry save(JournalEntry journalEntry);
    Optional<JournalEntry> findById(Long id);
    List<JournalEntry> findByUserId(Long userId);
    List<JournalEntry> findAll();
    void deleteById(Long id);
    List<JournalEntry> findByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
}