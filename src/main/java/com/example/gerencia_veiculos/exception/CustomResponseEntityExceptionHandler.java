package com.example.gerencia_veiculos.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final @Nullable ResponseEntity<DetalhesErro> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        DetalhesErro detalhesErro = new DetalhesErro(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(detalhesErro, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VeiculoNaoEncontradoException.class)
    public final @Nullable ResponseEntity<DetalhesErro> handleVeiculoNaoEncontradoException(Exception ex, WebRequest request) throws Exception {
        DetalhesErro detalhesErro = new DetalhesErro(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(detalhesErro, HttpStatus.NOT_FOUND);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        DetalhesErro detalhesErro = new DetalhesErro(LocalDateTime.now(), ex.getFieldError().getDefaultMessage(), request.getDescription(false));

        return new ResponseEntity<>(detalhesErro, HttpStatus.BAD_REQUEST);
    }

}
