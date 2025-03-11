package com.rentalservice.exception;

import com.rentalservice.dto.ErroCampo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedTupleException.class)
    public ResponseEntity<ErrorDetails> handleDuplicatedTupleException(DuplicatedTupleException ex) {
        ErrorDetails erro = new ErrorDetails(
                "Duplicated",
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGeneralException(Exception ex) {
      log.error("Erro inesperadp", ex);
       ErrorDetails erro = new ErrorDetails(
                "Internal Server Error",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorDetails erro = new ErrorDetails(
                "Not Found",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ErrorDetails> handleValidacaoException(ValidacaoException ex) {
        ErrorDetails erro = new ErrorDetails(
                "Validation Error",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Erro de validação: {}", e.getMessage());

        List<ErroCampo> listaErros = e.getFieldErrors()
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), Optional.ofNullable(fe.getDefaultMessage()).orElse("Mensagem de erro não disponível")))
                .collect(Collectors.toList());

        ErrorDetails erro = new ErrorDetails(
                "Validation Error",
                "Houve problemas de validação nos campos.",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                listaErros
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException e) {
        // Criar um erro detalhado com base na exceção
        ErrorDetails errorDetails = new ErrorDetails(
                "Access Denied",
                "Você não tem permissão para acessar esse recurso.",
                HttpStatus.FORBIDDEN.value(),
                null
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
    }


}
