package com.ewerton.sistema_de_agendamentos.exception;

public class InvalidAppointmentStateException extends RuntimeException {
    public InvalidAppointmentStateException(String message) { super(message);}
}
