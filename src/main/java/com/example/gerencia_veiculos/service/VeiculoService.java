package com.example.gerencia_veiculos.service;

import com.example.gerencia_veiculos.domain.Veiculo;
import com.example.gerencia_veiculos.domain.dto.VeiculoFiltro;
import com.example.gerencia_veiculos.repository.VeiculoRepository;
import com.example.gerencia_veiculos.repository.specification.VeiculoSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;
    private final CambioService cambioService;

    public VeiculoService(VeiculoRepository repository, CambioService cambioService) {
        this.repository = repository;
        this.cambioService = cambioService;
    }

    public Page<Veiculo> getVeiculos(VeiculoFiltro filtro, Pageable pageable) {
        return repository.findAll(VeiculoSpecifications.filtrar(filtro), pageable);
    }

    @Transactional
    public Veiculo salvar(Veiculo v) {
        if (repository.existsByPlaca(v.getPlaca())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Placa duplicada");
        }

        if (v.getPrecoBrl() != null) {
            v.setPrecoUsd(converterParaUsd(v.getPrecoBrl()));
        }

        return repository.save(v);
    }

    @Transactional
    public Veiculo atualizarTotal(Long id, Veiculo novosDados) {
        Veiculo existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!novosDados.getPlaca().equals(existente.getPlaca()) && repository.existsByPlaca(novosDados.getPlaca())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nova placa já em uso");
        }

        existente.setPlaca(novosDados.getPlaca());
        existente.setMarca(novosDados.getMarca());
        existente.setAno(novosDados.getAno());
        existente.setCor(novosDados.getCor());

        if (novosDados.getPrecoBrl() != null) {
            existente.setPrecoUsd(converterParaUsd(novosDados.getPrecoBrl()));
        }

        return repository.save(existente);
    }

    @Transactional
    public Veiculo atualizarParcial(Long id, Veiculo patch) {
        Veiculo existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (patch.getPlaca() != null) existente.setPlaca(patch.getPlaca());
        if (patch.getMarca() != null) existente.setMarca(patch.getMarca());
        if (patch.getAno() != null) existente.setAno(patch.getAno());
        if (patch.getCor() != null) existente.setCor(patch.getCor());

        if (patch.getPrecoBrl() != null) {
            existente.setPrecoUsd(converterParaUsd(patch.getPrecoBrl()));
        }

        return repository.save(existente);
    }

    public void remover(Long id) {
        if (!repository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        repository.deleteById(id);
    }

    private BigDecimal converterParaUsd(BigDecimal precoBrl) {
        BigDecimal taxa = cambioService.obterCotacao();
        return precoBrl.divide(taxa, 2, RoundingMode.HALF_UP);
    }
}