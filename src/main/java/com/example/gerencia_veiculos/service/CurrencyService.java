package com.example.gerencia_veiculos.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_PRIMARY = "https://economia.awesomeapi.com.br/json/last/USD-BRL";
    private static final String API_FALLBACK = "https://api.frankfurter.app/latest?from=USD&to=BRL";

    @Cacheable(value = "dollarRate")
    public BigDecimal getDollarRate() {
        try {
            JsonNode response = restTemplate.getForObject(API_PRIMARY, JsonNode.class);
            return new BigDecimal(response.path("USDBRL").path("bid").asText());
        } catch (Exception e) {
            try {
                JsonNode response = restTemplate.getForObject(API_FALLBACK, JsonNode.class);
                return new BigDecimal(response.path("rates").path("BRL").asText());
            } catch (Exception ex) {
                throw new RuntimeException("Falha ao obter cotação do dólar.");
            }
        }
    }
}