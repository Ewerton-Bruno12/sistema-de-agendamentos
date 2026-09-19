package com.ewerton.sistema_de_agendamentos.service;

import com.ewerton.sistema_de_agendamentos.dto.AppointmentRequestDto;
import com.ewerton.sistema_de_agendamentos.dto.AppointmentResponseDto;
import com.ewerton.sistema_de_agendamentos.enums.AppointmentStatus;
import com.ewerton.sistema_de_agendamentos.exception.InvalidAppointmentStateException;
import com.ewerton.sistema_de_agendamentos.exception.ResourceNotFoundException;
import com.ewerton.sistema_de_agendamentos.exception.TimeSlotUnavailableException;
import com.ewerton.sistema_de_agendamentos.model.AppointmentEntity;
import com.ewerton.sistema_de_agendamentos.model.ClientEntity;
import com.ewerton.sistema_de_agendamentos.model.ServiceEntity;
import com.ewerton.sistema_de_agendamentos.repository.AppointmentRepository;
import com.ewerton.sistema_de_agendamentos.repository.ClientRepository;
import com.ewerton.sistema_de_agendamentos.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;

    @Transactional(readOnly = true)
    public List<AppointmentResponseDto> findAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponseDto findById(Long id) {
        return appointmentRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "O agendamento com o ID " + id + " não foi encontrado"));
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDto> findByClientId(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException(
                    "Cliente com o ID " + clientId + " não foi encontrado");
        }

        return appointmentRepository.findByClientId(clientId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public AppointmentResponseDto save(AppointmentRequestDto appointmentRequestDto) {
        ClientEntity client = clientRepository.findById(appointmentRequestDto.clientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente com o ID " + appointmentRequestDto.clientId() + " não foi encontrado"));

        ServiceEntity service = serviceRepository.findById(appointmentRequestDto.serviceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Serviço com o ID " + appointmentRequestDto.serviceId() + " não foi encontrado"));


        boolean slotUnavailable = appointmentRepository.existsByServiceIdAndDateTime(
                appointmentRequestDto.serviceId(),
                appointmentRequestDto.dateTime()
        );

        if (slotUnavailable) {
            throw new TimeSlotUnavailableException(
                    "O horário solicitado já está ocupado para o serviço: " + service.getName());
        }

        AppointmentEntity newAppointment = AppointmentEntity.builder()
                .client(client)
                .service(service)
                .dateTime(appointmentRequestDto.dateTime())
                .status(AppointmentStatus.SCHEDULED) // Definido no Service
                .build();

        AppointmentEntity savedAppointment = appointmentRepository.save(newAppointment);
        return toDto(savedAppointment);
    }

    @Transactional
    public AppointmentResponseDto cancel(Long id) {
        AppointmentEntity appointmentEntity = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "O agendamento com o ID " + id + " não foi encontrado"));

        if (appointmentEntity.getStatus() == AppointmentStatus.CANCELED) {
            throw new InvalidAppointmentStateException("Este agendamento já se encontra cancelado");
        }

        appointmentEntity.setStatus(AppointmentStatus.CANCELED);

        return toDto(appointmentEntity);
    }

    private AppointmentResponseDto toDto(AppointmentEntity appointment) {
        return new AppointmentResponseDto(
                appointment.getId(),
                appointment.getClient().getName(),
                appointment.getService().getName(),
                appointment.getService().getPrice(),
                appointment.getDateTime(),
                appointment.getStatus()
        );
    }
}
