package com.tutorial.ia.domain.exception;

public class JournalEntryNotFoundException extends RuntimeException {
    public JournalEntryNotFoundException(String message) {
        super(message);
    }
    
    public JournalEntryNotFoundException(Long entryId) {
        super("Journal entry not found with id: " + entryId);
    }
}