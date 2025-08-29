package com.tutorial.ia.domain.port.in;

import com.tutorial.ia.domain.model.Goal;
import com.tutorial.ia.domain.model.GoalType;

import java.util.List;
import java.util.Optional;

public interface GoalManagementUseCase {
    Goal createGoal(String title, String description, Long userId, GoalType goalType);
    Goal updateGoal(Long id, String title, String description, GoalType goalType);
    void deleteGoal(Long id);
    Optional<Goal> getGoalById(Long id);
    List<Goal> getUserGoals(Long userId);
}
