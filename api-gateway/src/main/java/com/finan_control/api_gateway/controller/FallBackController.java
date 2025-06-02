package com.finan_control.api_gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável por gerenciar os endpoints de fallback dos serviços
 * 
 * Este controlador fornece endpoints que são chamados quando os serviços 
 * downstream estão indisponíveis, retornando mensagens apropriadas para o cliente.
 * 
 * Cada método manipula o fallback de um serviço específico:
 * - Serviço de Autenticação (/auth-fallback)
 * - Serviço de Orçamento (/budget-fallback) 
 * - Serviço de Categorias (/category-fallback)
 * - Serviço de Moedas (/currency-fallback)
 * - Serviço Financeiro (/finance-fallback)
 * - Serviço de Notificações (/notification-fallback)
 * - Serviço de Usuários (/user-fallback)
 *
 * Todos os endpoints retornam um ResponseEntity com status HTTP 503 (Service Unavailable)
 * e uma mensagem indicando qual serviço está indisponível.
 */

@RestController
public class FallBackController {

    @GetMapping("/auth-fallback")
    public ResponseEntity<String> authFallback() {
        return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Auth service is down");
    }

    @GetMapping("/budget-fallback")
    public ResponseEntity<String> budgetFallback() {
        return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Budget service is down");
    }

    @GetMapping("/category-fallback")
    public ResponseEntity<String> categoryFallback() {
        return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Category service is down");
    }   

    @GetMapping("/currency-fallback")
    public ResponseEntity<String> currencyFallback() {
        return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Currency service is down");
    }

    @GetMapping("/finance-fallback")
    public ResponseEntity<String> financeFallback() {
        return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Finance service is down");
    }

    @GetMapping("/notification-fallback")
    public ResponseEntity<String> notificationFallback() {
        return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Notification service is down");
    }
    
    @GetMapping("/user-fallback")
    public ResponseEntity<String> userFallback() {
        return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("User service is down");
    }
    
}