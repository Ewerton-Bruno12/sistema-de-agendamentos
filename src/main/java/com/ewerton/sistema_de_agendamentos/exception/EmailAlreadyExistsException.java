package com.ewerton.sistema_de_agendamentos.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) { super(message);}
}
