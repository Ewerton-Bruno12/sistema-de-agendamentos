package com.ewerton.sistema_de_agendamentos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientRequestDto(
        @NotBlank(message = "O nome do cliente é obrigatório")
        String name,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "E-mail inválido")
        String email,
        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "Telefone inválido. Use apenas números com DDD (10 ou 11 dígitos)"
        )
        String phone
) {}
