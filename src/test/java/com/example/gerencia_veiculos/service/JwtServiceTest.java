package com.example.gerencia_veiculos.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void deveGerarEValidarTokenComSucesso() {
        String username = "username";
        String role = "ADMIN";

        String token = jwtService.gerarToken(username, role);
        String extractedUser = jwtService.extrairUsername(token);
        String extractedRole = jwtService.extrairPerfil(token);

        assertNotNull(token);
        assertEquals(username, extractedUser);
        assertEquals(role, extractedRole);
        assertTrue(jwtService.isTokenValido(token));
    }

    @Test
    void deveRetornarFalsoParaTokenExpiradoOuInvalido() {
        assertFalse(jwtService.isTokenValido("token.invalido.aqui"));
    }
}