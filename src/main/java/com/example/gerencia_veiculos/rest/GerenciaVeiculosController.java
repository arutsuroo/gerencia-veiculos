package com.example.gerencia_veiculos.rest;

import com.example.gerencia_veiculos.domain.Veiculo;
import com.example.gerencia_veiculos.domain.dto.MarcaRelatorioDTO;
import com.example.gerencia_veiculos.domain.dto.VeiculoFiltro;
import com.example.gerencia_veiculos.repository.VeiculoRepository;
import com.example.gerencia_veiculos.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class GerenciaVeiculosController {

    private final VeiculoService service;
    private final VeiculoRepository repository;

    public GerenciaVeiculosController(VeiculoService service, VeiculoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/veiculos")
    public ResponseEntity<Page<Veiculo>> getVeiculos(
            VeiculoFiltro filtro,
            @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.getVeiculos(filtro, pageable));
    }

    @GetMapping("/veiculos/{id}")
    public ResponseEntity<Veiculo> getVeiculo(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/veiculos")
    public ResponseEntity<Veiculo> incluiVeiculo(@Valid @RequestBody Veiculo veiculo) {
        Veiculo salvo = service.salvar(veiculo);
        return ResponseEntity.created(getLocation(salvo)).body(salvo);
    }

    @PutMapping("/veiculos/{id}")
    public ResponseEntity<Veiculo> atualizaVeiculo(@PathVariable Long id, @Valid @RequestBody Veiculo novosDados) {
        return ResponseEntity.ok(service.atualizarTotal(id, novosDados));
    }

    @PatchMapping("/veiculos/{id}")
    public ResponseEntity<Veiculo> atualizaDadosVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculoPatch) {
        return ResponseEntity.ok(service.atualizarParcial(id, veiculoPatch));
    }

    @DeleteMapping("/veiculos/{id}")
    public ResponseEntity<Void> removeVeiculo(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/veiculos/relatorios/por-marca")
    public ResponseEntity<List<MarcaRelatorioDTO>> getRelatorioPorMarca() {
        return ResponseEntity.ok(repository.countVeiculosByMarca());
    }

    private URI getLocation(Veiculo salvo) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();
    }

}