package com.tutorial.ia.application.service;

import com.tutorial.ia.domain.exception.JournalEntryNotFoundException;
import com.tutorial.ia.domain.exception.ReportAlreadyExistsException;
import com.tutorial.ia.domain.model.AnalysisReport;
import com.tutorial.ia.domain.model.JournalEntry;
import com.tutorial.ia.domain.model.ReportType;
import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.domain.port.in.AnalysisReportManagementUseCase;
import com.tutorial.ia.domain.port.out.AiServicePort;
import com.tutorial.ia.domain.port.out.AnalysisReportRepositoryPort;
import com.tutorial.ia.domain.port.out.JournalEntryRepositoryPort;
import com.tutorial.ia.domain.port.out.UserRepositoryPort;
import com.tutorial.ia.infrastructure.service.PromptTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalysisReportService implements AnalysisReportManagementUseCase {

    private final AnalysisReportRepositoryPort analysisReportRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final JournalEntryRepositoryPort journalEntryRepositoryPort;
    private final AiServicePort aiServicePort;
    private final PromptTemplateService promptTemplateService;

    @Override
    public AnalysisReport generateDailyReport(User user, LocalDate date) {
        // 1. Verify if exists
        if (analysisReportRepositoryPort.existsByUserAndTypeAndPeriod(user, ReportType.DAILY, date, date)) {
            throw new ReportAlreadyExistsException("Daily report already exists for user " + user.getId());
        }

        // 2. Find daily journal entries
        List<JournalEntry> dayEntries = journalEntryRepositoryPort.findByUserAndCreatedAtBetween(user, date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        if (dayEntries.isEmpty()) {
            throw new JournalEntryNotFoundException("No entries found for day " + date);
        }

        // 3. Generar Analisis con IA
        String combinedEntries = dayEntries.stream()
                .map(entry -> "Title: " + entry.getTitle() + "\nContenido: " + entry.getEntry())
                .collect(Collectors.joining("\n\n--\n\n"));

        String analysis = generateDailyAnalysis(combinedEntries, date);

        // 4. Crear guardar informe
        AnalysisReport report = new AnalysisReport(user, ReportType.DAILY, date, date, analysis, "", "", "");
        return analysisReportRepositoryPort.save(report);
    }

    @Override
    public AnalysisReport generateWeeklyReport(User user, LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        
        // 1. Verificar si ya existe
        if (analysisReportRepositoryPort.existsByUserAndTypeAndPeriod(user, ReportType.WEEKLY, startOfWeek, endOfWeek)) {
            throw new ReportAlreadyExistsException("Weekly report already exists for week starting " + startOfWeek);
        }
        
        // 2. Obtener informes diarios de la semana
        List<AnalysisReport> dailyReports = new java.util.ArrayList<>();
        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            analysisReportRepositoryPort.findByUserAndTypeAndPeriod(user, ReportType.DAILY, date, date)
                .ifPresent(dailyReports::add);
        }
        
        if (dailyReports.isEmpty()) {
            throw new JournalEntryNotFoundException("No daily reports found for week starting " + startOfWeek);
        }
        
        // 3. Generar análisis semanal agregando reportes diarios
        String combinedReports = dailyReports.stream()
            .map(report -> "Día " + report.getPeriodStart() + ":\n" + report.getContent())
            .collect(Collectors.joining("\n\n---\n\n"));
            
        String analysis = generateWeeklyAnalysis(combinedReports, startOfWeek, endOfWeek);
        
        // 4. Crear y guardar informe semanal
        AnalysisReport report = new AnalysisReport(user, ReportType.WEEKLY, startOfWeek, endOfWeek, analysis, "", "", "");
        return analysisReportRepositoryPort.save(report);
    }

    @Override
    public AnalysisReport generateMonthlyReport(User user, int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        
        // 1. Verificar si ya existe
        if (analysisReportRepositoryPort.existsByUserAndTypeAndPeriod(user, ReportType.MONTHLY, startOfMonth, endOfMonth)) {
            throw new ReportAlreadyExistsException("Monthly report already exists for " + year + "-" + month);
        }
        
        // 2. Obtener informes semanales del mes
        List<AnalysisReport> weeklyReports = analysisReportRepositoryPort.findByUserAndType(user, ReportType.WEEKLY)
            .stream()
            .filter(report -> !report.getPeriodStart().isBefore(startOfMonth) && !report.getPeriodEnd().isAfter(endOfMonth))
            .collect(Collectors.toList());
            
        if (weeklyReports.isEmpty()) {
            throw new JournalEntryNotFoundException("No weekly reports found for month " + year + "-" + month);
        }
        
        // 3. Generar análisis mensual
        String combinedReports = weeklyReports.stream()
            .map(report -> "Semana " + report.getPeriodStart() + " - " + report.getPeriodEnd() + ":\n" + report.getContent())
            .collect(Collectors.joining("\n\n---\n\n"));
            
        String analysis = generateMonthlyAnalysis(combinedReports, year, month);
        
        // 4. Crear y guardar informe mensual
        AnalysisReport report = new AnalysisReport(user, ReportType.MONTHLY, startOfMonth, endOfMonth, analysis, "", "", "");
        return analysisReportRepositoryPort.save(report);
    }

    @Override
    public AnalysisReport generateYearlyReport(User user, int year) {
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);
        
        // 1. Verificar si ya existe
        if (analysisReportRepositoryPort.existsByUserAndTypeAndPeriod(user, ReportType.YEARLY, startOfYear, endOfYear)) {
            throw new ReportAlreadyExistsException("Yearly report already exists for " + year);
        }
        
        // 2. Obtener informes mensuales del año
        List<AnalysisReport> monthlyReports = analysisReportRepositoryPort.findByUserAndType(user, ReportType.MONTHLY)
            .stream()
            .filter(report -> report.getPeriodStart().getYear() == year)
            .collect(Collectors.toList());
            
        if (monthlyReports.isEmpty()) {
            throw new JournalEntryNotFoundException("No monthly reports found for year " + year);
        }
        
        // 3. Generar análisis anual
        String combinedReports = monthlyReports.stream()
            .map(report -> "Mes " + report.getPeriodStart().getMonth() + ":\n" + report.getContent())
            .collect(Collectors.joining("\n\n---\n\n"));
            
        String analysis = generateYearlyAnalysis(combinedReports, year);
        
        // 4. Crear y guardar informe anual
        AnalysisReport report = new AnalysisReport(user, ReportType.YEARLY, startOfYear, endOfYear, analysis, "", "", "");
        return analysisReportRepositoryPort.save(report);
    }

    @Override
    public Optional<AnalysisReport> getDailyReport(User user, LocalDate date) {
        return analysisReportRepositoryPort.findByUserAndTypeAndPeriod(user, ReportType.DAILY, date, date);
    }

    @Override
    public List<AnalysisReport> getWeeklyReports(User user, int year) {
        return analysisReportRepositoryPort.findByUserAndType(user, ReportType.WEEKLY)
            .stream()
            .filter(report -> report.getPeriodStart().getYear() == year)
            .collect(Collectors.toList());
    }

    @Override
    public List<AnalysisReport> getMonthlyReports(User user, int year) {
        return analysisReportRepositoryPort.findByUserAndType(user, ReportType.MONTHLY)
            .stream()
            .filter(report -> report.getPeriodStart().getYear() == year)
            .collect(Collectors.toList());
    }

    @Override
    public List<AnalysisReport> getYearlyReports(User user) {
        return analysisReportRepositoryPort.findByUserAndType(user, ReportType.YEARLY);
    }

    @Override
    public AnalysisReport regenerateReport(Long reportId) {
        // Buscar el informe existente
        AnalysisReport existingReport = analysisReportRepositoryPort.findById(reportId)
            .orElseThrow(() -> new JournalEntryNotFoundException("Report not found: " + reportId));
            
        // Regenerar según el tipo de informe
        switch (existingReport.getType()) {
            case DAILY -> {
                // Eliminar el existente y generar uno nuevo
                analysisReportRepositoryPort.deleteById(reportId);
                return generateDailyReport(existingReport.getUser(), existingReport.getPeriodStart());
            }
            case WEEKLY -> {
                analysisReportRepositoryPort.deleteById(reportId);
                return generateWeeklyReport(existingReport.getUser(), existingReport.getPeriodStart());
            }
            case MONTHLY -> {
                analysisReportRepositoryPort.deleteById(reportId);
                return generateMonthlyReport(existingReport.getUser(), 
                    existingReport.getPeriodStart().getYear(), 
                    existingReport.getPeriodStart().getMonthValue());
            }
            case YEARLY -> {
                analysisReportRepositoryPort.deleteById(reportId);
                return generateYearlyReport(existingReport.getUser(), existingReport.getPeriodStart().getYear());
            }
            default -> throw new IllegalArgumentException("Unknown report type: " + existingReport.getType());
        }
    }

    @Override
    public void deleteReport(Long reportId) {
        if (!analysisReportRepositoryPort.findById(reportId).isPresent()) {
            throw new JournalEntryNotFoundException("Report not found: " + reportId);
        }
        analysisReportRepositoryPort.deleteById(reportId);
    }

    private String generateDailyAnalysis(String entries, LocalDate date) {
        Map<String, Object> variables = Map.of(
                "entries", entries,
                "date", date.toString()
        );

        String prompt = promptTemplateService.loadTemplate("reports/daily-report", variables);
        return aiServicePort.generateResponse(prompt);
    }
    
    private String generateWeeklyAnalysis(String combinedReports, LocalDate startOfWeek, LocalDate endOfWeek) {
        Map<String, Object> variables = Map.of(
                "reports", combinedReports,
                "startDate", startOfWeek.toString(),
                "endDate", endOfWeek.toString()
        );
        
        String prompt = promptTemplateService.loadTemplate("reports/weekly-report", variables);
        return aiServicePort.generateResponse(prompt);
    }
    
    private String generateMonthlyAnalysis(String combinedReports, int year, int month) {
        Map<String, Object> variables = Map.of(
                "reports", combinedReports,
                "year", year,
                "month", month
        );
        
        String prompt = promptTemplateService.loadTemplate("reports/monthly-report", variables);
        return aiServicePort.generateResponse(prompt);
    }
    
    private String generateYearlyAnalysis(String combinedReports, int year) {
        Map<String, Object> variables = Map.of(
                "reports", combinedReports,
                "year", year
        );
        
        String prompt = promptTemplateService.loadTemplate("reports/yearly-report", variables);
        return aiServicePort.generateResponse(prompt);
    }
}
