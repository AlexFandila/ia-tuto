package com.tutorial.ia.infrastructure.adapter.out.persistence.entity;

import com.tutorial.ia.domain.model.ReportType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "analysis_reports")
@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = {"user"})
public class AnalysisReportJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    private LocalDate periodStart;    // Inicio del período que cubre el informe

    private LocalDate periodEnd;      // Final del período que cubre el informe

    @Lob
    private String content;           // Contenido resumido y analizado por IA

    @Lob
    private String insights;          // Insights clave extraídos

    @Lob
    private String emotions;          // Emociones principales identificadas

    @Lob
    private String topics;            // Temas principales del período

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
