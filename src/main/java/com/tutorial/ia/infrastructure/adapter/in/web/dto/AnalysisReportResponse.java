package com.tutorial.ia.infrastructure.adapter.in.web.dto;

import com.tutorial.ia.domain.model.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para informes de análisis.
 * Representa la información de un informe que se envía al cliente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisReportResponse {
    private Long id;
    private Long userId;
    private String username;
    private ReportType type;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String content;
    private String insights;
    private String emotions;
    private String topics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}