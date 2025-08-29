package com.tutorial.ia.infrastructure.adapter.out.persistence.mapper;

import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "journalEntries", ignore = true)
    @Mapping(target = "goals", ignore = true)
    User toDomain(UserJpaEntity entity);
    
    @Mapping(target = "journalEntries", ignore = true)
    @Mapping(target = "goals", ignore = true)
    UserJpaEntity toEntity(User user);
    
    @Named("toBasicUser")
    @Mapping(target = "journalEntries", ignore = true)
    @Mapping(target = "goals", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toBasicUser(UserJpaEntity entity);
}