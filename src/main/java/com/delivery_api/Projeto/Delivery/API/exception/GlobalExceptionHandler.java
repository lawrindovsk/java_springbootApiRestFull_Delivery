package com.delivery_api.Projeto.Delivery.API.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Diz ao Spring: "Eu cuido dos erros de todos os Controllers"
public class GlobalExceptionHandler {

    // Trata erros de validação (@Valid) - Erro 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // Cria um mapa para guardar os erros (ex: "nome": "O nome é obrigatório")
        Map<String, String> errors = new HashMap<>();

        // Pega cada campo que falhou e adiciona no mapa
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Monta a resposta final padronizada
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erro de Validação");
        response.put("details", errors); // Coloca a lista de campos errados aqui dentro;

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value()); // 404;
        response.put("error", "Recurso não encontrado");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }// trata erros de "not found" e retorna 404 not found;


    // da para adicionar outros tratamentos aqui depois (ex: cliente não encontrado);
}