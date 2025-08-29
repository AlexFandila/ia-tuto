package com.tutorial.ia.infrastructure.adapter.out.persistence.mapper;

import com.tutorial.ia.domain.model.Goal;
import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.GoalJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface GoalMapper {
    
    @Mapping(target = "user", source = "user", qualifiedByName = "toBasicUser")
    Goal toDomain(GoalJpaEntity entity);
    
    @Mapping(target = "user", ignore = true)
    @Named("toDomainWithoutUser")
    Goal toDomainWithoutUser(GoalJpaEntity entity);
    
    @Mapping(target = "user.journalEntries", ignore = true)
    @Mapping(target = "user.goals", ignore = true)
    @Mapping(target = "user.analysisReports", ignore = true)
    GoalJpaEntity toEntity(Goal goal);
}
