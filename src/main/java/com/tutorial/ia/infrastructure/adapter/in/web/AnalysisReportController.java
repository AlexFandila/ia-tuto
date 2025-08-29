package com.tutorial.ia.infrastructure.adapter.in.web;

import com.tutorial.ia.domain.model.AnalysisReport;
import com.tutorial.ia.domain.port.in.AnalysisReportManagementUseCase;
import com.tutorial.ia.domain.port.out.UserRepositoryPort;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.AnalysisReportResponse;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.AnalysisReportDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de informes de análisis.
 * Proporciona endpoints para generar, consultar y gestionar informes jerárquicos.
 */
@RestController
@RequestMapping("/api/v1/analysis-reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnalysisReportController {

    private final AnalysisReportManagementUseCase analysisReportUseCase;
    private final UserRepositoryPort userRepositoryPort;
    private final AnalysisReportDtoMapper dtoMapper;

    // ================================
    // ENDPOINTS DE GENERACIÓN
    // ================================

    /**
     * Genera un informe diario para un usuario específico
     * POST /api/v1/analysis-reports/daily?userId=1&date=2024-01-15
     */
    @PostMapping("/daily")
    public ResponseEntity<AnalysisReportResponse> generateDailyReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        var user = userRepositoryPort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
        AnalysisReport report = analysisReportUseCase.generateDailyReport(user, date);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(dtoMapper.toResponse(report));
    }

    /**
     * Genera un informe semanal
     * POST /api/v1/analysis-reports/weekly?userId=1&startOfWeek=2024-01-15
     */
    @PostMapping("/weekly")
    public ResponseEntity<AnalysisReportResponse> generateWeeklyReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek) {
        
        var user = userRepositoryPort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
        AnalysisReport report = analysisReportUseCase.generateWeeklyReport(user, startOfWeek);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(dtoMapper.toResponse(report));
    }

    /**
     * Genera un informe mensual
     * POST /api/v1/analysis-reports/monthly?userId=1&year=2024&month=1
     */
    @PostMapping("/monthly")
    public ResponseEntity<AnalysisReportResponse> generateMonthlyReport(
            @RequestParam Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        
        var user = userRepositoryPort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
        AnalysisReport report = analysisReportUseCase.generateMonthlyReport(user, year, month);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(dtoMapper.toResponse(report));
    }

    /**
     * Genera un informe anual
     * POST /api/v1/analysis-reports/yearly?userId=1&year=2024
     */
    @PostMapping("/yearly")
    public ResponseEntity<AnalysisReportResponse> generateYearlyReport(
            @RequestParam Long userId,
            @RequestParam int year) {
        
        var user = userRepositoryPort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
        AnalysisReport report = analysisReportUseCase.generateYearlyReport(user, year);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(dtoMapper.toResponse(report));
    }

    // ================================
    // ENDPOINTS DE CONSULTA
    // ================================

    /**
     * Obtiene un informe diario específico
     * GET /api/v1/analysis-reports/daily?userId=1&date=2024-01-15
     */
    @GetMapping("/daily")
    public ResponseEntity<AnalysisReportResponse> getDailyReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        var user = userRepositoryPort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
        Optional<AnalysisReport> report = analysisReportUseCase.getDailyReport(user, date);
        return report.map(r -> ResponseEntity.ok(dtoMapper.toResponse(r)))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todos los informes semanales de un año
     * GET /api/v1/analysis-reports/weekly?userId=1&year=2024
     */
    @GetMapping("/weekly")
    public ResponseEntity<List<AnalysisReportResponse>> getWeeklyReports(
            @RequestParam Long userId,
            @RequestParam int year) {
        
        var user = userRepositoryPort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
        List<AnalysisReport> reports = analysisReportUseCase.getWeeklyReports(user, year);
        List<AnalysisReportResponse> responses = reports.stream()
            .map(dtoMapper::toResponse)
            .toList();
            
        return ResponseEntity.ok(responses);
    }

    /**
     * Obtiene todos los informes mensuales de un año
     * GET /api/v1/analysis-reports/monthly?userId=1&year=2024
     */
    @GetMapping("/monthly")
    public ResponseEntity<List<AnalysisReportResponse>> getMonthlyReports(
            @RequestParam Long userId,
            @RequestParam int year) {
        
        var user = userRepositoryPort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
        List<AnalysisReport> reports = analysisReportUseCase.getMonthlyReports(user, year);
        List<AnalysisReportResponse> responses = reports.stream()
            .map(dtoMapper::toResponse)
            .toList();
            
        return ResponseEntity.ok(responses);
    }

    /**
     * Obtiene todos los informes anuales de un usuario
     * GET /api/v1/analysis-reports/yearly?userId=1
     */
    @GetMapping("/yearly")
    public ResponseEntity<List<AnalysisReportResponse>> getYearlyReports(
            @RequestParam Long userId) {
        
        var user = userRepositoryPort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
        List<AnalysisReport> reports = analysisReportUseCase.getYearlyReports(user);
        List<AnalysisReportResponse> responses = reports.stream()
            .map(dtoMapper::toResponse)
            .toList();
            
        return ResponseEntity.ok(responses);
    }

    /**
     * Obtiene todos los informes de un usuario
     * GET /api/v1/analysis-reports/user/1
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AnalysisReportResponse>> getAllUserReports(
            @PathVariable Long userId) {
        
        var user = userRepositoryPort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
        // Necesitamos un método en el puerto para obtener todos los informes por usuario
        // Por ahora, implementamos una solución temporal
        List<AnalysisReport> allReports = new java.util.ArrayList<>();
        allReports.addAll(analysisReportUseCase.getYearlyReports(user));
        // Aquí podrías agregar otros tipos si tuvieras un método findByUser en el puerto
        
        List<AnalysisReportResponse> responses = allReports.stream()
            .map(dtoMapper::toResponse)
            .toList();
            
        return ResponseEntity.ok(responses);
    }

    // ================================
    // ENDPOINTS DE GESTIÓN
    // ================================

    /**
     * Regenera un informe existente
     * PUT /api/v1/analysis-reports/1/regenerate
     */
    @PutMapping("/{reportId}/regenerate")
    public ResponseEntity<AnalysisReportResponse> regenerateReport(@PathVariable Long reportId) {
        AnalysisReport report = analysisReportUseCase.regenerateReport(reportId);
        return ResponseEntity.ok(dtoMapper.toResponse(report));
    }

    /**
     * Elimina un informe
     * DELETE /api/v1/analysis-reports/1
     */
    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        analysisReportUseCase.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }

    // ================================
    // ENDPOINT DE SALUD
    // ================================

    /**
     * Health check del servicio de informes
     * GET /api/v1/analysis-reports/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Analysis Reports Service is running");
    }
}