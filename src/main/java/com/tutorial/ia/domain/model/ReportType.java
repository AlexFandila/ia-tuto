package com.tutorial.ia.domain.model;

/**
 * Tipos de informes disponibles en el sistema.
 * Sigue una jerarquía temporal donde cada nivel superior agrega información del nivel inferior.
 */
public enum ReportType {
    DAILY,      // Se genera diariamente desde las entradas del diario
    WEEKLY,     // Se genera semanalmente agregando informes diarios
    MONTHLY,    // Se genera mensualmente agregando informes semanales
    YEARLY      // Se genera anualmente agregando informes mensuales
}