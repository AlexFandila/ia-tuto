package com.tutorial.ia.application.service;

import com.tutorial.ia.domain.model.JournalEntry;
import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.domain.port.in.JournalManagementUseCase;
import com.tutorial.ia.domain.port.out.JournalEntryRepositoryPort;
import com.tutorial.ia.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JournalEntryService implements JournalManagementUseCase {

    private final JournalEntryRepositoryPort journalEntryRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public JournalEntry createJournalEntry(String title, String entry, Long userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        JournalEntry journalEntry = new JournalEntry(title, entry, user);
        return journalEntryRepositoryPort.save(journalEntry);
    }

    @Override
    public Optional<JournalEntry> getJournalEntryById(Long id) {
        return journalEntryRepositoryPort.findById(id);
    }

    @Override
    public List<JournalEntry> getUserJournalEntries(Long userId) {
        return journalEntryRepositoryPort.findByUserId(userId);
    }

    @Override
    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepositoryPort.findAll();
    }

    @Override
    public JournalEntry updateJournalEntry(Long id, String title, String entry) {
        JournalEntry journalEntry = journalEntryRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Journal entry not found"));
        
        journalEntry.setTitle(title);
        journalEntry.setEntry(entry);
        return journalEntryRepositoryPort.save(journalEntry);
    }

    @Override
    public void deleteJournalEntry(Long id) {
        journalEntryRepositoryPort.deleteById(id);
    }
}