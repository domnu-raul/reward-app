package com.rewardapp.backend.controllers;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    private ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        Error e = new Error(ex.getClass().getName(), ex.getMessage(), LocalDateTime.now());
        EntityModel<Error> body = EntityModel.of(e);

        return handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    private static class Error {
        private final String error_class;
        private final String message;
        private final LocalDateTime timestamp;

        public Error(String error_class, String message, LocalDateTime timestamp) {
            this.error_class = error_class;
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getError_class() {
            return error_class;
        }

        public String getMessage() {
            return message;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }

}
