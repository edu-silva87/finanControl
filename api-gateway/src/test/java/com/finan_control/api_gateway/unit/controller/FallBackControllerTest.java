package com.finan_control.api_gateway.unit.controller;

import com.finan_control.api_gateway.config.SecurityConfig;
import com.finan_control.api_gateway.config.TestSecurityConfig;
import com.finan_control.api_gateway.controller.FallBackController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Testes unitários para o controlador de fallback do API Gateway
 * 
 * Esta classe realiza testes unitários para verificar o comportamento do controlador
 * de fallback do API Gateway, que fornece respostas padrão quando um serviço está fora do ar.
 * 
**/
@WebFluxTest(controllers = FallBackController.class)
@Import({SecurityConfig.class, TestSecurityConfig.class})
@ActiveProfiles("test")
public class FallBackControllerTest {
    
    @Autowired
    private WebTestClient webTestClient;

    private static final String TEST_JWT = "Bearer test-token";
    
    @Test
    void testAuthFallback() {
        webTestClient.get()
            .uri("/auth-fallback")
            .header("Authorization", TEST_JWT)
            .exchange()
            .expectStatus().isEqualTo(503)
            .expectBody(String.class)
            .isEqualTo("Auth service is down");
    }

    @Test
    void testBudgetFallback() {
        webTestClient.get()
            .uri("/budget-fallback")
            .header("Authorization", TEST_JWT)
            .exchange()
            .expectStatus().isEqualTo(503)
            .expectBody(String.class)
            .isEqualTo("Budget service is down");
    }

    @Test
    void testCategoryFallback() {
        webTestClient.get()
            .uri("/category-fallback")
            .header("Authorization", TEST_JWT)
            .exchange()
            .expectStatus().isEqualTo(503)
            .expectBody(String.class)
            .isEqualTo("Category service is down");
    }

    @Test
    void testCurrencyFallback() {
        webTestClient.get()
            .uri("/currency-fallback")
            .header("Authorization", TEST_JWT)
            .exchange()
            .expectStatus().isEqualTo(503)
            .expectBody(String.class)
            .isEqualTo("Currency service is down");
    }

    @Test
    void testFinanceFallback() {
        webTestClient.get()
            .uri("/finance-fallback")
            .header("Authorization", TEST_JWT)
            .exchange()
            .expectStatus().isEqualTo(503)
            .expectBody(String.class)
            .isEqualTo("Finance service is down");
    }

    @Test
    void testNotificationFallback() {
        webTestClient.get()
            .uri("/notification-fallback")
            .header("Authorization", TEST_JWT)
            .exchange()
            .expectStatus().isEqualTo(503)
            .expectBody(String.class)
            .isEqualTo("Notification service is down");
    }

    @Test
    void testUserFallback() {
        webTestClient.get()
            .uri("/user-fallback")
            .header("Authorization", TEST_JWT)
            .exchange()
            .expectStatus().isEqualTo(503)
            .expectBody(String.class)
            .isEqualTo("User service is down");
    }
}
