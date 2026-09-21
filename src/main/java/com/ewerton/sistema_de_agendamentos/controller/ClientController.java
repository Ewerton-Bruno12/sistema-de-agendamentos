package com.ewerton.sistema_de_agendamentos.controller;

import com.ewerton.sistema_de_agendamentos.dto.ClientRequestDto;
import com.ewerton.sistema_de_agendamentos.dto.ClientResponseDto;
import com.ewerton.sistema_de_agendamentos.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDto createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        return clientService.save(clientRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponseDto> findAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientResponseDto findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientResponseDto updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequestDto clientRequestDto) {
        return clientService.update(id, clientRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }
}
