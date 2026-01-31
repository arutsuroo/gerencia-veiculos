package com.example.gerencia_veiculos.exception;

import com.example.gerencia_veiculos.domain.dto.ErrorPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorPayload> handleResponseStatus(ResponseStatusException ex) {
        ErrorPayload error = new ErrorPayload(
                ex.getStatusCode().value(),
                ex.getStatusCode().toString(),
                ex.getReason()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }
}