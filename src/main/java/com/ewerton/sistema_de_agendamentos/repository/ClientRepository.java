package com.ewerton.sistema_de_agendamentos.repository;

import com.ewerton.sistema_de_agendamentos.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    boolean existsByEmail(String email);
}
