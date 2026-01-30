package com.example.gerencia_veiculos.repository;

import com.example.gerencia_veiculos.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
