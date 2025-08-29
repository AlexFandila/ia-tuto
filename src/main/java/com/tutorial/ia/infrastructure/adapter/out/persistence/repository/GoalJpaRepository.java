package com.tutorial.ia.infrastructure.adapter.out.persistence.repository;

import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.GoalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalJpaRepository extends JpaRepository<GoalJpaEntity, Long> {
    List<GoalJpaEntity> findByUserId(Long userId);
}
