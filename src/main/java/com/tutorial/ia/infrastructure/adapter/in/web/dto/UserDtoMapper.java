package com.tutorial.ia.infrastructure.adapter.in.web.dto;

import com.tutorial.ia.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    
    UserResponse toResponse(User user);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "journalEntries", ignore = true)
    @Mapping(target = "goals", ignore = true)
    @Mapping(target = "analysisReports", ignore = true)
    User fromCreateRequest(CreateUserRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "journalEntries", ignore = true)
    @Mapping(target = "goals", ignore = true)
    @Mapping(target = "analysisReports", ignore = true)
    User fromUpdateRequest(UpdateUserRequest request);
}