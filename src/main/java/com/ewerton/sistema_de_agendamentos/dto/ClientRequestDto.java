package com.ewerton.sistema_de_agendamentos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientRequestDto(
        @NotBlank(message = "O nome do cliente é obrigatório")
        String name,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "E-mail inválido")
        String email,
        @NotBlank(message = "O telefone é obrigatório")
        String phone
) {}
