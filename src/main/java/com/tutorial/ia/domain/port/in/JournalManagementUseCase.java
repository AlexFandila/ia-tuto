package com.tutorial.ia.domain.port.in;

import com.tutorial.ia.domain.model.JournalEntry;
import java.util.List;
import java.util.Optional;

public interface JournalManagementUseCase {
    JournalEntry createJournalEntry(String title, String entry, Long userId);
    Optional<JournalEntry> getJournalEntryById(Long id);
    List<JournalEntry> getUserJournalEntries(Long userId);
    List<JournalEntry> getAllJournalEntries();
    JournalEntry updateJournalEntry(Long id, String title, String entry);
    void deleteJournalEntry(Long id);
}