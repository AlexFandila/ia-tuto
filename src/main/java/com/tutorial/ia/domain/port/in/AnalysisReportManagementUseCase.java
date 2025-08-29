package com.tutorial.ia.domain.port.in;

import com.tutorial.ia.domain.model.AnalysisReport;
import com.tutorial.ia.domain.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnalysisReportManagementUseCase {

    // Casos de uso principales del negocio
    AnalysisReport generateDailyReport(User user, LocalDate date);
    AnalysisReport generateWeeklyReport(User user, LocalDate startOfWeek);
    AnalysisReport generateMonthlyReport(User user, int year, int month);
    AnalysisReport generateYearlyReport(User user, int year);

    // Consultas
    Optional<AnalysisReport> getDailyReport(User user, LocalDate date);
    List<AnalysisReport> getWeeklyReports(User user, int year);
    List<AnalysisReport> getMonthlyReports(User user, int year);
    List<AnalysisReport> getYearlyReports(User user);

    // Regenerar informes
    AnalysisReport regenerateReport(Long reportId);

    // Gestión
    void deleteReport(Long reportId);
}
