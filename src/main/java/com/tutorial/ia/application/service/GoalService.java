package com.tutorial.ia.application.service;

import com.tutorial.ia.domain.exception.GoalNotFoundException;
import com.tutorial.ia.domain.exception.UserNotFoundException;
import com.tutorial.ia.domain.model.Goal;
import com.tutorial.ia.domain.model.GoalType;
import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.domain.port.in.GoalManagementUseCase;
import com.tutorial.ia.domain.port.out.GoalRepositoryPort;
import com.tutorial.ia.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoalService implements GoalManagementUseCase {

    private final GoalRepositoryPort goalRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public Goal createGoal(String title, String description, Long userId, GoalType goalType) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Goal goal = new Goal(title, description, user, goalType);
        return goalRepositoryPort.save(goal);
    }

    @Override
    public Goal updateGoal(Long id, String title, String description, GoalType goalType) {
        Goal goal = goalRepositoryPort.findById(id)
                .orElseThrow(() -> new GoalNotFoundException(id));

        goal.setTitle(title);
        goal.setDescription(description);
        goal.setGoalType(goalType);
        goal.setId(id);

        return goalRepositoryPort.save(goal);
    }

    @Override
    public void deleteGoal(Long id) {
        goalRepositoryPort.deleteById(id);
    }

    @Override
    public Optional<Goal> getGoalById(Long id) {
        return goalRepositoryPort.findById(id);
    }

    @Override
    public List<Goal> getUserGoals(Long userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return goalRepositoryPort.findByUserId(userId);
    }
}
