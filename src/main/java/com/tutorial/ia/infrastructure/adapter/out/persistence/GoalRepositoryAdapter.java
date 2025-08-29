package com.tutorial.ia.infrastructure.adapter.out.persistence;

import com.tutorial.ia.domain.model.Goal;
import com.tutorial.ia.domain.port.out.GoalRepositoryPort;
import com.tutorial.ia.infrastructure.adapter.out.persistence.mapper.GoalMapper;
import com.tutorial.ia.infrastructure.adapter.out.persistence.repository.GoalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GoalRepositoryAdapter implements GoalRepositoryPort {

    private final GoalJpaRepository goalJpaRepository;
    private final GoalMapper goalMapper;


    @Override
    public Goal save(Goal goal) {
        return goalMapper.toDomain(goalJpaRepository.save(goalMapper.toEntity(goal)));
    }

    @Override
    public Optional<Goal> findById(Long id) {
        return goalJpaRepository.findById(id)
                .map(goalMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        goalJpaRepository.deleteById(id);
    }

    @Override
    public List<Goal> findByUserId(Long userId) {
        return goalJpaRepository.findByUserId(userId).stream()
                .map(goalMapper::toDomain)
                .toList();
    }
}
