package com.ewerton.sistema_de_agendamentos.dto;

import java.math.BigDecimal;

public record ServiceResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer durationMinutes
) {}
