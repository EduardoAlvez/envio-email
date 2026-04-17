package com.example.envio_email.exceptionHandler.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldError> fieldErrors;

    // Construtor completo
    public ErrorResponse(LocalDateTime timestamp, int status, String error,
                         String message, String path,
                         List<FieldError> fieldErrors) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = timestamp.format(formatter);
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }

    // Construtor simplificado
    public ErrorResponse(LocalDateTime timestamp, int status, String error,
                         String message, String path) {
        this(timestamp, status, error, message, path, null);
    }

    // Getters
    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    // Setters
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    // Classe interna FieldError
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldError {
        private String field;
        private String message;
        private String rejectedValue;

        public FieldError(String field, String message, String rejectedValue) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
        }

        // Getters
        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public String getRejectedValue() {
            return rejectedValue;
        }

        // Setters
        public void setField(String field) {
            this.field = field;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setRejectedValue(String rejectedValue) {
            this.rejectedValue = rejectedValue;
        }
    }
}