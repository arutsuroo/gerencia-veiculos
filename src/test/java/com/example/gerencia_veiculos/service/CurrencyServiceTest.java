package com.example.gerencia_veiculos.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyService currencyService;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(currencyService, "restTemplate", restTemplate);
    }

    @Test
    void deveRetornarCotacaoPelaApiPrimaria() throws Exception {
        String json = "{\"USDBRL\":{\"bid\":\"5.25\"}}";
        JsonNode node = mapper.readTree(json);

        when(restTemplate.getForObject(contains("awesomeapi"), eq(JsonNode.class)))
                .thenReturn(node);

        BigDecimal rate = currencyService.getDollarRate();

        assertEquals(new BigDecimal("5.25"), rate);
        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }

    @Test
    void deveRetornarCotacaoPelaApiFallbackQuandoAPrimariaFalhar() throws Exception {

        String jsonFallback = "{\"rates\":{\"BRL\":5.30}}";
        JsonNode nodeFallback = mapper.readTree(jsonFallback);

        when(restTemplate.getForObject(contains("awesomeapi"), eq(JsonNode.class)))
                .thenThrow(new RuntimeException("API Down"));

        when(restTemplate.getForObject(contains("frankfurter"), eq(JsonNode.class)))
                .thenReturn(nodeFallback);

        BigDecimal rate = currencyService.getDollarRate();

        assertEquals(new BigDecimal("5.3"), rate);
        verify(restTemplate, times(2)).getForObject(anyString(), any());
    }

    @Test
    void deveLancarExcecaoQuandoAmbasApisFalharem() {
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class)))
                .thenThrow(new RuntimeException("Total Failure"));

        assertThrows(RuntimeException.class, () -> currencyService.getDollarRate());
    }
}