package com.example.gerencia_veiculos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VeiculoNaoEncontradoException extends RuntimeException{

    public VeiculoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
