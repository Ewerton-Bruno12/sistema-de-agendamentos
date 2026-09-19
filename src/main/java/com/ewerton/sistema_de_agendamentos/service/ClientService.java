package com.ewerton.sistema_de_agendamentos.service;

import com.ewerton.sistema_de_agendamentos.dto.ClientRequestDto;
import com.ewerton.sistema_de_agendamentos.dto.ClientResponseDto;
import com.ewerton.sistema_de_agendamentos.exception.EmailAlreadyExistsException;
import com.ewerton.sistema_de_agendamentos.exception.ResourceNotFoundException;
import com.ewerton.sistema_de_agendamentos.model.ClientEntity;
import com.ewerton.sistema_de_agendamentos.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<ClientResponseDto> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClientResponseDto findById(Long id) {
        return clientRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente com o ID " + id + " não foi encontrado"));
    }

    @Transactional
    public ClientResponseDto save(ClientRequestDto clientRequestDto) {
        if (clientRepository.existsByEmail(clientRequestDto.email())) {
            throw new EmailAlreadyExistsException(
                    "O email '" + clientRequestDto.email() + "' já foi cadastrado");
        }

        ClientEntity newClient = ClientEntity.builder()
                .name(clientRequestDto.name())
                .email(clientRequestDto.email())
                .phone(clientRequestDto.phone())
                .build();

        ClientEntity savedClient = clientRepository.save(newClient);
        return toDto(savedClient);
    }

    @Transactional
    public ClientResponseDto update(Long id, ClientRequestDto clientRequestDto) {
        ClientEntity existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente com o ID " + id + " não foi encontrado"));

        if (!existingClient.getEmail().equals(clientRequestDto.email()) &&
                clientRepository.existsByEmail(clientRequestDto.email())) {
            throw new EmailAlreadyExistsException(
                    "O email '" + clientRequestDto.email() + "' já foi cadastrado");
        }

        existingClient.setName(clientRequestDto.name());
        existingClient.setEmail(clientRequestDto.email());
        existingClient.setPhone(clientRequestDto.phone());

        return toDto(existingClient);
    }

    @Transactional
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Cliente com o ID " + id + " não foi encontrado");
        }

        clientRepository.deleteById(id);
    }

    private ClientResponseDto toDto(ClientEntity clientEntity) {
        return new ClientResponseDto(
                clientEntity.getId(),
                clientEntity.getName(),
                clientEntity.getEmail(),
                clientEntity.getPhone(),
                clientEntity.getCreatedAt()
        );
    }
}
