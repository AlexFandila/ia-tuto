package com.tutorial.ia.infrastructure.adapter.in.web.dto;

import com.tutorial.ia.domain.model.AnalysisReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para convertir entre entidades de dominio de AnalysisReport y DTOs web.
 * Utiliza MapStruct para generar automáticamente las implementaciones de mapeo.
 */
@Mapper(componentModel = "spring")
public interface AnalysisReportDtoMapper {

    /**
     * Convierte una entidad de dominio AnalysisReport a AnalysisReportResponse
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    AnalysisReportResponse toResponse(AnalysisReport analysisReport);
}