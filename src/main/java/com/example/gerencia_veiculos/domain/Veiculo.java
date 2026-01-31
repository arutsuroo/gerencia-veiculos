package com.example.gerencia_veiculos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import java.math.BigDecimal;

@Entity
@Table(name = "veiculos")
@SoftDelete(columnName = "ativo", strategy = SoftDeleteType.ACTIVE)
@Getter
@Setter
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String placa;

    private String marca;
    private Integer ano;
    private String cor;

    private BigDecimal precoUsd;

    @Transient
    private BigDecimal precoBrl;

    @Column(name = "ativo", insertable = false, updatable = false)
    private boolean ativo = true;
}