package com.tutorial.ia.infrastructure.adapter.out.persistence.repository;

import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.JournalEntryJpaEntity;
import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JournalEntryJpaRepository extends JpaRepository<JournalEntryJpaEntity, Long> {
    List<JournalEntryJpaEntity> findByUserId(Long userId);
    List<JournalEntryJpaEntity> findByUserAndCreatedAtBetween(UserJpaEntity user, LocalDateTime start, LocalDateTime end);
}