package com.example.gerencia_veiculos.repository.specification;

import com.example.gerencia_veiculos.domain.Veiculo;
import com.example.gerencia_veiculos.domain.dto.VeiculoFiltro;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VeiculoSpecifications {

    public static Specification<Veiculo> filtrar(VeiculoFiltro filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getMarca() != null && !filtro.getMarca().isBlank()) {
                predicates.add(cb.equal(root.get("marca"), filtro.getMarca()));
            }
            if (filtro.getAno() != null) {
                predicates.add(cb.equal(root.get("ano"), filtro.getAno()));
            }
            if (filtro.getCor() != null && !filtro.getCor().isBlank()) {
                predicates.add(cb.equal(root.get("cor"), filtro.getCor()));
            }

            if (filtro.getMinPreco() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("preco"), filtro.getMinPreco()));
            }
            if (filtro.getMaxPreco() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("preco"), filtro.getMaxPreco()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
