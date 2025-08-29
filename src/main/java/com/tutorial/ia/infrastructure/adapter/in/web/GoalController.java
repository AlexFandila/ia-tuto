package com.tutorial.ia.infrastructure.adapter.in.web;

import com.tutorial.ia.domain.model.Goal;
import com.tutorial.ia.domain.model.GoalType;
import com.tutorial.ia.domain.port.in.GoalManagementUseCase;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.CreateGoalRequest;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.GoalDtoMapper;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.GoalResponse;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.UpdateGoalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalManagementUseCase goalManagementUseCase;
    private final GoalDtoMapper goalDtoMapper;

    @PostMapping
    public ResponseEntity<GoalResponse> createGoal(@RequestBody CreateGoalRequest request) {
        try {
            Goal goalFromRequest = goalDtoMapper.fromCreateRequest(request);
            Goal goal = goalManagementUseCase.createGoal(
                    goalFromRequest.getTitle(),
                    goalFromRequest.getDescription(),
                    request.getUserId(),
                    goalFromRequest.getGoalType()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(goalDtoMapper.toResponse(goal));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalResponse> updateGoal(@RequestBody UpdateGoalRequest req, @PathVariable Long id) {
        try {
            Goal goal = goalManagementUseCase.updateGoal(
                    id, req.getTitle(), req.getDescription(), GoalType.valueOf(req.getGoalType())
            );
            return ResponseEntity.ok(goalDtoMapper.toResponse(goal));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalManagementUseCase.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponse> getGoalResponseById(@PathVariable Long id) {
        return goalManagementUseCase.getGoalById(id)
                .map(goal -> ResponseEntity.ok(goalDtoMapper.toResponse(goal)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GoalResponse>> getUserGoals(@PathVariable Long userId) {
        try {
            List<GoalResponse> goals = goalManagementUseCase.getUserGoals(userId).stream()
                    .map(goalDtoMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(goals);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
