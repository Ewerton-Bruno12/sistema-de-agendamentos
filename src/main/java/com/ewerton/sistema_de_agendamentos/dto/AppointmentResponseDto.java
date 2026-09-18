package com.ewerton.sistema_de_agendamentos.dto;

import com.ewerton.sistema_de_agendamentos.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AppointmentResponseDto(
        Long id,
        String clientName,
        String serviceName,
        BigDecimal price,
        LocalDateTime dateTime,
        AppointmentStatus status
) {}
