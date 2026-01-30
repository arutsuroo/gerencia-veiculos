package com.example.gerencia_veiculos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "veiculos")
@Data
@NoArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 2, message = "A marca precisa ter no mínimo dois caracteres")
    private String marca;

    private Integer ano;

    @Size(min = 2, message = "A cor precisa ter no mínimo dois caracteres")
    private String cor;
    private BigDecimal preco;
}
