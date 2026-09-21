package com.ewerton.sistema_de_agendamentos.controller;

import com.ewerton.sistema_de_agendamentos.dto.AppointmentRequestDto;
import com.ewerton.sistema_de_agendamentos.dto.AppointmentResponseDto;
import com.ewerton.sistema_de_agendamentos.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
@RequiredArgsConstructor
@Validated
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDto createAppointment(@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        return appointmentService.save(appointmentRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponseDto> findAll() {
        return appointmentService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentResponseDto findById(@PathVariable Long id) {
        return appointmentService.findById(id);
    }

    @GetMapping("/client/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponseDto> findByClientId(@PathVariable Long clientId) {
        return appointmentService.findByClientId(clientId);
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentResponseDto cancel(@PathVariable Long id) {
        return appointmentService.cancel(id);
    }
}
