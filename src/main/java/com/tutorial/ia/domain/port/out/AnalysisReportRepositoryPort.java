package com.tutorial.ia.domain.port.out;

import com.tutorial.ia.domain.model.AnalysisReport;
import com.tutorial.ia.domain.model.ReportType;
import com.tutorial.ia.domain.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnalysisReportRepositoryPort {
    AnalysisReport save(AnalysisReport report);
    
    Optional<AnalysisReport> findById(Long id);

    void deleteById(Long id);

    List<AnalysisReport> findByUser(User user);

    // Buscar reporte especifico por periodo
    Optional<AnalysisReport> findByUserAndTypeAndPeriod(User user, ReportType type, LocalDate periodStart, LocalDate periodEnd);

    // Verificar si existe un reporte
    boolean existsByUserAndTypeAndPeriod(User user, ReportType type, LocalDate periodStart, LocalDate periodEnd);

    List<AnalysisReport> findByUserAndType(User user, ReportType type);

}
