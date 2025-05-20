package dev.atendimentoAPI.atendimento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    // Erros de validação (ex: campos inválidos com @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> erros = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

    // Exceção de recurso não encontrado
    @ExceptionHandler(AtendimentoNaoEncontradoException.class)
    public ResponseEntity<Object> handleAtendimentoNaoEncontrado(AtendimentoNaoEncontradoException exception) {
        Map<String, String> erro = Map.of("erro", exception.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    // Exceções genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception, WebRequest request) {
        Map<String, String> erro = Map.of("erro", exception.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
