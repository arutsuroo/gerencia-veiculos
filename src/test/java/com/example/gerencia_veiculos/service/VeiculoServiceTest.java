package com.example.gerencia_veiculos.service;

import com.example.gerencia_veiculos.domain.Veiculo;
import com.example.gerencia_veiculos.repository.VeiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository repository;

    @InjectMocks
    private VeiculoService service;

    @Test
    void deveLancarExcecaoAoBuscarVeiculoInexistente() {
        lenient().when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> {
            service.remover(1L);
        });
    }

    @Test
    void deveAtualizarApenasAlgunsCamposNoPatch() {
        Veiculo existente = new Veiculo();
        existente.setCor("Azul");

        Veiculo patch = new Veiculo();
        patch.setCor("Vermelho");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Veiculo resultado = service.atualizarParcial(1L, patch);

        assertEquals("Vermelho", resultado.getCor());
        verify(repository).save(any());
    }
}