package com.tutorial.ia.infrastructure.adapter.out.persistence.mapper;

import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    
    private final JournalEntryMapper journalEntryMapper;
    
    public User toDomain(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getJournalEntries() != null) {
            user.setJournalEntries(entity.getJournalEntries().stream()
                    .map(journalEntryMapper::toDomainWithoutUser)
                    .collect(Collectors.toList()));
        }
        
        return user;
    }
    
    public UserJpaEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());
        
        return entity;
    }
}