package com.tutorial.ia.infrastructure.adapter.out.persistence;

import com.tutorial.ia.domain.model.JournalEntry;
import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.domain.port.out.JournalEntryRepositoryPort;
import com.tutorial.ia.infrastructure.adapter.out.persistence.mapper.JournalEntryMapper;
import com.tutorial.ia.infrastructure.adapter.out.persistence.mapper.UserMapper;
import com.tutorial.ia.infrastructure.adapter.out.persistence.repository.JournalEntryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JournalEntryRepositoryAdapter implements JournalEntryRepositoryPort {

    private final JournalEntryJpaRepository journalEntryJpaRepository;
    private final JournalEntryMapper journalEntryMapper;
    private final UserMapper userMapper;

    @Override
    public JournalEntry save(JournalEntry journalEntry) {
        return journalEntryMapper.toDomain(
                journalEntryJpaRepository.save(journalEntryMapper.toEntity(journalEntry)));
    }

    @Override
    public Optional<JournalEntry> findById(Long id) {
        return journalEntryJpaRepository.findById(id)
                .map(journalEntryMapper::toDomain);
    }

    @Override
    public List<JournalEntry> findByUserId(Long userId) {
        return journalEntryJpaRepository.findByUserId(userId).stream()
                .map(journalEntryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<JournalEntry> findAll() {
        return journalEntryJpaRepository.findAll().stream()
                .map(journalEntryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        journalEntryJpaRepository.deleteById(id);
    }

    @Override
    public List<JournalEntry> findByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end) {
        return journalEntryJpaRepository.findByUserAndCreatedAtBetween(
                        userMapper.toEntity(user), start, end) // ← Convertir a JPA entity
                .stream().map(journalEntryMapper::toDomain).toList();

    }

}