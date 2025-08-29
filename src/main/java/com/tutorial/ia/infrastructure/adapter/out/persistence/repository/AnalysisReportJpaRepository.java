package com.tutorial.ia.infrastructure.adapter.out.persistence.repository;

import com.tutorial.ia.domain.model.ReportType;
import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.AnalysisReportJpaEntity;
import com.tutorial.ia.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnalysisReportJpaRepository extends JpaRepository<AnalysisReportJpaEntity, Long> {
    List<AnalysisReportJpaEntity> findByUser(UserJpaEntity user);
    List<AnalysisReportJpaEntity> findByUserAndType(UserJpaEntity user, ReportType type);
    Optional<AnalysisReportJpaEntity> findByUserAndTypeAndPeriodStartAndPeriodEnd(UserJpaEntity user, ReportType type, LocalDate periodStart, LocalDate periodEnd);
    boolean existsByUserAndTypeAndPeriodStartAndPeriodEnd(UserJpaEntity user, ReportType type, LocalDate periodStart, LocalDate periodEnd);
}
