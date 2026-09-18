package com.ewerton.sistema_de_agendamentos.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequestDto(
        @NotNull(message = "O ID do cliente é obrigatório")
        Long clientId,
        @NotNull(message = "O ID do serviço é obrigatório")
        Long serviceId,
        @Future(message = "A data deve ser futura")
        @NotNull(message = "A data do agendamento é obrigatória")
        LocalDateTime dateTime
) {}
