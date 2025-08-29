package com.tutorial.ia.domain.exception;

public class ReportAlreadyExistsException extends RuntimeException {
    public ReportAlreadyExistsException(String message) {
        super(message);
    }

    public ReportAlreadyExistsException(Long reportId) {
        super("Report already exists with id: " + reportId);
    }
}
