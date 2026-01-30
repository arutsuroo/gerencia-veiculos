package com.example.gerencia_veiculos.rest;

import com.example.gerencia_veiculos.domain.Veiculo;
import com.example.gerencia_veiculos.domain.dto.VeiculoFiltro;
import com.example.gerencia_veiculos.repository.VeiculoRepository;
import com.example.gerencia_veiculos.repository.specification.VeiculoSpecifications;
import jakarta.validation.Valid;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class GerenciaVeiculosController {
    private VeiculoRepository repository;

    public GerenciaVeiculosController(VeiculoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/veiculos")
    public ResponseEntity<List<Veiculo>> getVeiculos(VeiculoFiltro filtro) {
        Specification<Veiculo> spec = VeiculoSpecifications.filtrar(filtro);
        List<Veiculo> veiculos = repository.findAll(spec);
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping(path = "/veiculos/{id}")
    public ResponseEntity<Veiculo> getVeiculo(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<Veiculo> atualizaVeiculo(@PathVariable Long id, @Valid @RequestBody Veiculo veiculoNovosDados) {
        return repository.findById(id)
                .map(veiculoExistente -> {
                    veiculoNovosDados.setId(id);
                    Veiculo atualizado = repository.save(veiculoNovosDados);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping(path = "/veiculos/{id}")
    public ResponseEntity<Veiculo> atualizaDadosVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculoPatch) {
        return repository.findById(id)
                .map(veiculoExistente -> {
                    if (veiculoPatch.getMarca() != null) veiculoExistente.setMarca(veiculoPatch.getMarca());
                    if (veiculoPatch.getAno() != null) veiculoExistente.setAno(veiculoPatch.getAno());
                    if (veiculoPatch.getCor() != null) veiculoExistente.setCor(veiculoPatch.getCor());
                    if (veiculoPatch.getPreco() != null) veiculoExistente.setPreco(veiculoPatch.getPreco());

                    Veiculo atualizado = repository.save(veiculoExistente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/veiculos/{id}")
    public void removeVeiculo(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
