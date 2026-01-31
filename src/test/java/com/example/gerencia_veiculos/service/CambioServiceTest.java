package com.example.gerencia_veiculos.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CambioServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CambioService cambioService;

    @Test
    void deveRetornarCotacaoPadraoEmCasoDeFalha() {

        BigDecimal cotacao = cambioService.obterCotacao();

        assertNotNull(cotacao);
        assertTrue(cotacao.compareTo(BigDecimal.ZERO) > 0);
    }
}