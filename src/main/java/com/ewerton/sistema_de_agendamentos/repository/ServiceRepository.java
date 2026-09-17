package com.ewerton.sistema_de_agendamentos.repository;

import com.ewerton.sistema_de_agendamentos.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    boolean existsByName(String name);
}
