package com.tutorial.ia.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Goal {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isCompleted;
    private User user;
    private GoalType goalType;

    public Goal(String title, String description, User user, GoalType goalType) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.goalType = goalType;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
        this.updatedAt = LocalDateTime.now();
    }
}
