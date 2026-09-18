package com.ewerton.sistema_de_agendamentos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServiceRequestDto(
        @NotBlank(message = "O nome do serviço é obrigatório")
        String name,
        String description,
        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal price,
        @NotNull(message = "A duração em minutos é obrigatória")
        @Positive(message = "A duração deve ser maior que zero")
        Integer durationMinutes
) {}
