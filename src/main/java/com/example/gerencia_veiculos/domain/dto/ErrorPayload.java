package com.example.gerencia_veiculos.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorPayload {
    private int status;
    private String error;
    private String message;
}