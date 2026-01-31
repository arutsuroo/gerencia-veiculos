package com.example.gerencia_veiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GerenciaVeiculosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciaVeiculosApplication.class, args);
	}

}
