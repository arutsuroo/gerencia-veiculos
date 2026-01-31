package com.example.gerencia_veiculos.rest;

import com.example.gerencia_veiculos.service.JwtService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {

        String role = null;
        if ("admin".equals(request.username) && "admin123".equals(request.password)) {
            role = "ROLE_ADMIN";
        } else if ("user".equals(request.username) && "user123".equals(request.password)) {
            role = "ROLE_USER";
        }

        if (role != null) {
            String token = jwtService.gerarToken(request.username, role);
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).build();
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}