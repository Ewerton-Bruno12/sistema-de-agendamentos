package com.ewerton.sistema_de_agendamentos.service;

import com.ewerton.sistema_de_agendamentos.dto.ServiceRequestDto;
import com.ewerton.sistema_de_agendamentos.dto.ServiceResponseDto;
import com.ewerton.sistema_de_agendamentos.exception.ResourceNotFoundException;
import com.ewerton.sistema_de_agendamentos.exception.ServiceNameAlreadyExistsException;
import com.ewerton.sistema_de_agendamentos.model.ServiceEntity;
import com.ewerton.sistema_de_agendamentos.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Transactional(readOnly = true)
    public List<ServiceResponseDto> findAll() {
        return serviceRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceResponseDto findById(Long id) {
        return serviceRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Serviço com o ID " + id + " não foi encontrado"));
    }

    @Transactional
    public ServiceResponseDto save(ServiceRequestDto serviceRequestDto) {
        if (serviceRepository.existsByName(serviceRequestDto.name())) {
            throw new ServiceNameAlreadyExistsException(
                    "O serviço com o nome " + serviceRequestDto.name() + " já foi cadastrado");
        }

        ServiceEntity newService = ServiceEntity.builder()
                .name(serviceRequestDto.name())
                .description(serviceRequestDto.description())
                .price(serviceRequestDto.price())
                .durationMinutes(serviceRequestDto.durationMinutes())
                .build();

        ServiceEntity savedService = serviceRepository.save(newService);
        return toDto(savedService);
    }

    @Transactional
    public ServiceResponseDto update(Long id, ServiceRequestDto serviceRequestDto) {
        ServiceEntity existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "O serviço com o ID " + id + " não foi encontrado"));

        if (!existingService.getName().equals(serviceRequestDto.name()) &&
                serviceRepository.existsByName(serviceRequestDto.name())) {
            throw new ServiceNameAlreadyExistsException(
                    "O serviço com o nome " + serviceRequestDto.name() + " já foi cadastrado");
        }

        existingService.setName(serviceRequestDto.name());
        existingService.setDescription(serviceRequestDto.description());
        existingService.setPrice(serviceRequestDto.price());
        existingService.setDurationMinutes(serviceRequestDto.durationMinutes());

        return toDto(existingService);
    }

    @Transactional
    public void delete(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("O serviço com o ID " + id + " não foi encontrado");
        }

        serviceRepository.deleteById(id);
    }

    private ServiceResponseDto toDto(ServiceEntity service) {
        return new ServiceResponseDto(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getDurationMinutes()
        );
    }
}
