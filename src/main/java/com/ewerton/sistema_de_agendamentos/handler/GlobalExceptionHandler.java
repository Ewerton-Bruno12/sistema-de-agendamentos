package com.ewerton.sistema_de_agendamentos.handler;

import com.ewerton.sistema_de_agendamentos.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ProblemDetail buildProblem(HttpStatus status, String title, String detail) {
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        return buildProblem(HttpStatus.NOT_FOUND, "Recurso Não Encontrado", ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return buildProblem(HttpStatus.CONFLICT, "Conflito de Email", ex.getMessage());
    }

    @ExceptionHandler(ServiceNameAlreadyExistsException.class)
    public ProblemDetail handleServiceNameAlreadyExists(ServiceNameAlreadyExistsException ex) {
        return buildProblem(HttpStatus.CONFLICT, "Conflito de Nome de Serviço", ex.getMessage());
    }

    @ExceptionHandler(TimeSlotUnavailableException.class)
    public ProblemDetail handleTimeSlotUnavailable(TimeSlotUnavailableException ex) {
        return buildProblem(HttpStatus.CONFLICT, "Conflito de Agenda", ex.getMessage());
    }

    @ExceptionHandler(InvalidAppointmentStateException.class)
    public ProblemDetail handleInvalidAppointmentState(InvalidAppointmentStateException ex) {
        return buildProblem(HttpStatus.CONFLICT, "Estado Inválido do Agendamento", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido";
        String detail = "O parâmetro '" + ex.getName() + "' recebeu um valor inválido. Era esperado um valor do tipo " + requiredType + ".";

        return buildProblem(HttpStatus.BAD_REQUEST, "Tipo de Parâmetro Inválido", detail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationError(MethodArgumentNotValidException ex) {
        String detail = "Um ou mais campos estão inválidos. Faça a correção e tente novamente.";
        ProblemDetail problemDetail = buildProblem(HttpStatus.BAD_REQUEST, "Erro de Validação", detail);

        Map<String, String> invalidFields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Campo inválido",
                        (existing, replacement) -> existing
                ));

        problemDetail.setProperty("invalid_fields", invalidFields);
        return problemDetail;
    }
}
