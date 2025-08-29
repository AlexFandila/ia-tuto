package com.tutorial.ia.domain.port.out;

import com.tutorial.ia.domain.model.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalRepositoryPort {
    Goal save(Goal goal);
    Optional<Goal> findById(Long id);
    void deleteById(Long id);
    List<Goal> findByUserId(Long userId);
}
