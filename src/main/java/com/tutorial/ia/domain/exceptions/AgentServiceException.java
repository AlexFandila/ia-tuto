package com.tutorial.ia.domain.exceptions;

public class AgentServiceException extends RuntimeException {
    public AgentServiceException(String message) {
        super(message);
    }

    public AgentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
