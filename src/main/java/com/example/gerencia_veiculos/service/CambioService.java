package com.example.gerencia_veiculos.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.math.BigDecimal;

@Service
public class CambioService {

    private final RestClient restClient = RestClient.create();
    private final ObjectMapper mapper = new ObjectMapper();

    @Cacheable(value = "cotacaoDolar")
    public BigDecimal obterCotacao() {
        try {
            String json = restClient.get()
                    .uri("https://economia.awesomeapi.com.br/json/last/USD-BRL")
                    .retrieve()
                    .body(String.class);

            JsonNode root = mapper.readTree(json);
            return new BigDecimal(root.path("USDBRL").path("bid").asText());

        } catch (Exception e) {
            try {
                String jsonFallback = restClient.get()
                        .uri("https://api.frankfurter.app/latest?from=USD&to=BRL")
                        .retrieve()
                        .body(String.class);

                JsonNode rootFallback = mapper.readTree(jsonFallback);
                return new BigDecimal(rootFallback.path("rates").path("BRL").asText());
            } catch (Exception ex) {
                return new BigDecimal("5.00");
            }
        }
    }
}