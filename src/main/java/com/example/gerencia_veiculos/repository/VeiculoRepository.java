package com.example.gerencia_veiculos.repository;

import com.example.gerencia_veiculos.domain.Veiculo;
import com.example.gerencia_veiculos.domain.dto.MarcaRelatorioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {

    boolean existsByPlaca(String placa);

    @Query("SELECT v.marca as marca, COUNT(v) as quantidade FROM Veiculo v WHERE v.ativo = true GROUP BY v.marca")
    List<MarcaRelatorioDTO> countVeiculosByMarca();
}
