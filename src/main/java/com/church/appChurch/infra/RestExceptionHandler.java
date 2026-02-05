package com.church.appChurch.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // 1. Criar o Logger
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex){
        // Logar como WARN (Aviso), pois geralmente é erro do cliente (dados inválidos)
        logger.warn("Erro de validação (Bad Request): {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("erro", "Dados Inválidos");
        response.put("mensagem", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleGeneral(Exception ex) {
        // 2. Logar como ERROR com o Stack Trace completo (ex)
        // Isso é crucial para você descobrir BUGS no sistema
        logger.error("Erro não tratado no servidor: ", ex);

        Map<String, String> response = new HashMap<>();
        response.put("erro", "Erro Interno");
        response.put("mensagem", "Ocorreu um erro inesperado no servidor. Contate o suporte.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }



}
