package com.ewerton.sistema_de_agendamentos.repository;

import com.ewerton.sistema_de_agendamentos.model.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByClientId(Long clientId);
    boolean existsByServiceIdAndDateTime(Long serviceId, LocalDateTime dateTime);
}
