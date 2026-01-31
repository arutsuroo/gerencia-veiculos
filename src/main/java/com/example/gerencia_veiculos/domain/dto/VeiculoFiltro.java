package com.example.gerencia_veiculos.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VeiculoFiltro {
    private String marca;
    private Integer ano;
    private String cor;
    private BigDecimal minPreco;
    private BigDecimal maxPreco;
}
