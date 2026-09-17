package com.ewerton.sistema_de_agendamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SistemaDeAgendamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeAgendamentosApplication.class, args);
	}

}
