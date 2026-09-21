package com.ewerton.sistema_de_agendamentos.controller;

import com.ewerton.sistema_de_agendamentos.dto.ServiceRequestDto;
import com.ewerton.sistema_de_agendamentos.dto.ServiceResponseDto;
import com.ewerton.sistema_de_agendamentos.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/services")
@RequiredArgsConstructor
@Validated
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponseDto createService(@Valid @RequestBody ServiceRequestDto serviceRequestDto) {
        return serviceService.save(serviceRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceResponseDto> findAll() {
        return serviceService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponseDto findById(@PathVariable Long id) {
        return serviceService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponseDto updateService(@PathVariable Long id, @Valid @RequestBody ServiceRequestDto serviceRequestDto) {
        return serviceService.update(id, serviceRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id) {
        serviceService.delete(id);
    }
}
