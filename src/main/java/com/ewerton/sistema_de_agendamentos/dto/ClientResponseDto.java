package com.ewerton.sistema_de_agendamentos.dto;

import java.time.LocalDateTime;

public record ClientResponseDto(
        Long id,
        String name,
        String email,
        String phone,
        LocalDateTime createdAt
) {}
