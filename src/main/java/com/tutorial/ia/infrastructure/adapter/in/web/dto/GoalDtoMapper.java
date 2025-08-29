package com.tutorial.ia.infrastructure.adapter.in.web.dto;

import com.tutorial.ia.domain.model.Goal;
import com.tutorial.ia.domain.model.GoalType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GoalDtoMapper {
    
    GoalDtoMapper INSTANCE = Mappers.getMapper(GoalDtoMapper.class);
    
    @Mapping(source = "goalType", target = "goalType", qualifiedByName = "goalTypeToString")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    GoalResponse toResponse(Goal goal);
    
    @Mapping(source = "goalType", target = "goalType", qualifiedByName = "stringToGoalType")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "user", ignore = true)
    Goal fromCreateRequest(CreateGoalRequest request);
    
    @Named("goalTypeToString")
    default String goalTypeToString(GoalType goalType) {
        return goalType != null ? goalType.name() : null;
    }
    
    @Named("stringToGoalType")
    default GoalType stringToGoalType(String goalType) {
        if (goalType == null) return null;
        try {
            return GoalType.valueOf(goalType);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}