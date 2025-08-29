package com.tutorial.ia.infrastructure.adapter.out.persistence.entity;

import com.tutorial.ia.domain.model.GoalType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "goal")
@Data
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
public class GoalJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @Enumerated(EnumType.STRING)
    private GoalType goalType;

    private boolean isCompleted;

}
