package com.tutorial.ia.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa un informe de análisis de diarios.
 * Esta es la entidad central del dominio - no depende de frameworks externos.
 */
@Data
@NoArgsConstructor
public class AnalysisReport {
    private Long id;
    private User user;
    private ReportType type;
    private LocalDate periodStart;    // Inicio del período que cubre el informe
    private LocalDate periodEnd;      // Final del período que cubre el informe
    private String content;           // Contenido resumido y analizado por IA
    private String insights;          // Insights clave extraídos
    private String emotions;          // Emociones principales identificadas
    private String topics;            // Temas principales del período
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnalysisReport(User user, ReportType type, LocalDate periodStart, LocalDate periodEnd, 
                         String content, String insights, String emotions, String topics) {
        this.user = user;
        this.type = type;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.content = content;
        this.insights = insights;
        this.emotions = emotions;
        this.topics = topics;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Actualiza el contenido del informe y marca como actualizado
     */
    public void updateContent(String content, String insights, String emotions, String topics) {
        this.content = content;
        this.insights = insights;
        this.emotions = emotions;
        this.topics = topics;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Verifica si el informe cubre el período especificado
     */
    public boolean coversDate(LocalDate date) {
        return !date.isBefore(periodStart) && !date.isAfter(periodEnd);
    }
}