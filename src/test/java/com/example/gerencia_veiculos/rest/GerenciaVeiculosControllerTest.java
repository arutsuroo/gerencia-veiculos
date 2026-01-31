package com.example.gerencia_veiculos.rest;

import com.example.gerencia_veiculos.domain.Veiculo;
import com.example.gerencia_veiculos.repository.VeiculoRepository;
import com.example.gerencia_veiculos.service.VeiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GerenciaVeiculosControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VeiculoService service;

    @Mock
    private VeiculoRepository repository;

    @InjectMocks
    private GerenciaVeiculosController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void deveRetornarVeiculoPorIdComSucesso() throws Exception {
        Veiculo v = new Veiculo();
        v.setId(1L);
        v.setPlaca("UNO-9999");
        when(repository.findById(1L)).thenReturn(Optional.of(v));

        mockMvc.perform(get("/veiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placa").value("UNO-9999"));
    }

    @Test
    void deveRetornar404QuandoVeiculoNaoExiste() throws Exception {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/veiculos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveSalvarNovoVeiculoComSucesso() throws Exception {
        Veiculo v = new Veiculo();
        v.setId(5L);
        v.setPlaca("SAV-2026");
        when(service.salvar(any())).thenReturn(v);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(v)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5));
    }
}