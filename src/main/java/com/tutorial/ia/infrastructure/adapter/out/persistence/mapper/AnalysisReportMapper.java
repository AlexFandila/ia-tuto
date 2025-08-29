package com.tutorial.ia.infrastructure.adapter.out.persistence.mapper;

import com.tutorial.ia.domain.model.AnalysisReport;
import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.AnalysisReportJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AnalysisReportMapper {

    @Mapping(target = "user", source = "user", qualifiedByName = "toBasicUser")
    AnalysisReport toDomain(AnalysisReportJpaEntity entity);

    @Mapping(target = "user", ignore = true)
    AnalysisReport toDomainWithoutUser(AnalysisReportJpaEntity entity);

    @Mapping(target = "user.journalEntries", ignore = true)
    @Mapping(target = "user.goals", ignore = true)
    @Mapping(target = "user.analysisReports", ignore = true)
    AnalysisReportJpaEntity toEntity(AnalysisReport report);
}
