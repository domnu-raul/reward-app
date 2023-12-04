package com.rewardapp.backend.controllers;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ControllerAdvice
@ResponseBody
public class _ControllerExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<EntityModel<Error>> handleConflict(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Error e = new Error(status, ex.getClass().getName(), ex.getMessage(), Timestamp.valueOf(LocalDateTime.now()));
        EntityModel<Error> body = EntityModel.of(e);

        return ResponseEntity
                .status(status)
                .body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<EntityModel<Error>> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error e = new Error(status, ex.getClass().getName(), ex.getMessage(), Timestamp.valueOf(LocalDateTime.now()));
        EntityModel<Error> body = EntityModel.of(e);

        return ResponseEntity
                .status(status)
                .body(body);
    }

    private record Error(HttpStatus status, String error_class, String message, Timestamp timestamp) {

        public String getTimestamp() {
            return timestamp.toString();
        }
    }

}
