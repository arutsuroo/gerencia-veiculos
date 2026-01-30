package com.example.gerencia_veiculos.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DetalhesErro {

    private LocalDateTime timestamp;
    private String mensagem;
    private String detalhes;

}
