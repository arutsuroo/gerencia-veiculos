package com.example.gerencia_veiculos.rest;

import com.example.gerencia_veiculos.domain.Veiculo;
import com.example.gerencia_veiculos.exception.VeiculoNaoEncontradoException;
import com.example.gerencia_veiculos.repository.VeiculoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class GerenciaVeiculosController {
    private VeiculoRepository repository;

    public GerenciaVeiculosController(VeiculoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/veiculos")
    public List<Veiculo> getVeiculos() {
        return repository.findAll();
    }

    @GetMapping(path = "/veiculos?marca={marca}&ano={ano}&cor={cor}")
    public void getVeiculoPorParametro(@PathVariable String marca, @PathVariable Integer ano, @PathVariable String cor) {
        //TODO  - Retorna todos os veiculos de acordo com os parâmetros passados
    }

    @GetMapping(path = "/veiculos?minPreco={valorMinimo}&maxPreco={valorMaximo}")
    public void getVeiculosPorPreco(@PathVariable BigDecimal valorMinimo, @PathVariable BigDecimal valorMaximo) {
        //TODO  - Retorna todos os veículos de acordo com o range de preço
    }

    @GetMapping(path = "/veiculos/{id}")
    public Optional<Veiculo> getVeiculo(@PathVariable Long id) {
        Optional<Veiculo> veiculo = repository.findById(id);

        if (veiculo.isEmpty())
            throw new VeiculoNaoEncontradoException("id:"+id);
        return veiculo;
    }

    @PostMapping(path = "/veiculos")
    public ResponseEntity<Veiculo> incluiVeiculo(@Valid @RequestBody Veiculo veiculo) {
        Veiculo veiculoIncluido = repository.save(veiculo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(veiculoIncluido.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/veiculos/{id}")
    public void atualizaVeiculo(@PathVariable Long id) {
        //TODO  - Atualiza os dados de um veículo
    }

    @PatchMapping(path = "/veiculos/{id}")
    public void atualizaDadosVeiculo(@PathVariable Long id) {
        //TODO  - Atualiza parciamente os dados de um veículo
    }

    @DeleteMapping(path = "/veiculos/{id}")
    public void removeVeiculo(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping(path = "/veiculos/relatorios/por-marca")
    public void getVeiculoPorMarca() {
        //TODO  - Retorna relatório de quantidade de veículos agrupados por marca
    }
}
