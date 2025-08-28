package com.tutorial.ia.infrastructure.adapter.out.persistence.mapper;

import com.tutorial.ia.domain.model.JournalEntry;
import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.JournalEntryJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class JournalEntryMapper {
    
    public JournalEntry toDomain(JournalEntryJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setId(entity.getId());
        journalEntry.setTitle(entity.getTitle());
        journalEntry.setEntry(entity.getEntry());
        journalEntry.setCreatedAt(entity.getCreatedAt());
        journalEntry.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getUser() != null) {
            UserMapper userMapper = new UserMapper(this);
            journalEntry.setUser(userMapper.toDomain(entity.getUser()));
        }
        
        return journalEntry;
    }
    
    public JournalEntry toDomainWithoutUser(JournalEntryJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setId(entity.getId());
        journalEntry.setTitle(entity.getTitle());
        journalEntry.setEntry(entity.getEntry());
        journalEntry.setCreatedAt(entity.getCreatedAt());
        journalEntry.setUpdatedAt(entity.getUpdatedAt());
        
        return journalEntry;
    }
    
    public JournalEntryJpaEntity toEntity(JournalEntry journalEntry) {
        if (journalEntry == null) {
            return null;
        }
        
        JournalEntryJpaEntity entity = new JournalEntryJpaEntity();
        entity.setId(journalEntry.getId());
        entity.setTitle(journalEntry.getTitle());
        entity.setEntry(journalEntry.getEntry());
        entity.setCreatedAt(journalEntry.getCreatedAt());
        entity.setUpdatedAt(journalEntry.getUpdatedAt());
        
        if (journalEntry.getUser() != null) {
            UserMapper userMapper = new UserMapper(this);
            entity.setUser(userMapper.toEntity(journalEntry.getUser()));
        }
        
        return entity;
    }
}