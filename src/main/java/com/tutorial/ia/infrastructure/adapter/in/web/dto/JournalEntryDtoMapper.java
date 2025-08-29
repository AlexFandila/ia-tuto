package com.tutorial.ia.infrastructure.adapter.in.web.dto;

import com.tutorial.ia.domain.model.JournalEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JournalEntryDtoMapper {
    
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    JournalEntryResponse toResponse(JournalEntry journalEntry);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    JournalEntry fromCreateRequest(CreateJournalEntryRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    JournalEntry fromUpdateRequest(UpdateJournalEntryRequest request);
}