package com.finan_control.api_gateway.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Testes de integração para o API Gateway
 * 
 * Esta classe realiza testes de integração para verificar o comportamento do API Gateway
 * ao interagir com os serviços de autenticação, orçamento, categorias, moeda, finanças,
 * usuários e notificações.
**/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
public class ApiGatewayRoutesTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String TEST_JWT = "Bearer test-token";

    @Test
    void testAuthServiceLoginRoute() {
        // Configurar mock do serviço de autenticação
        stubFor(post(urlEqualTo("/auth/login"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"token\": \"test-token\"}")));

        // Testar a rota de login 
        webTestClient.post()
                .uri("/auth/login")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").isEqualTo("test-token");

        
    }

    @Test
    void testAuthServiceRegisterRoute() {
        // Configurar mock do serviço de autenticação
        stubFor(post(urlEqualTo("/auth/register"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"token\": \"test-token\"}")));

       // Testar a rota de registro
       webTestClient.post()
                .uri("/auth/register")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").isEqualTo("test-token");
    }


    @Test
    void testBudgetServiceRoute() {
        // Configurar mock do serviço de orçamento
        stubFor(get(urlEqualTo("/budget/budgets"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        // Testar a rota com autenticação
        webTestClient.get()
                .uri("/budget/budgets")
                .header("Authorization", TEST_JWT)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void testCategoryServiceRoute() {
        // Configurar mock do serviço de categorias
        stubFor(get(urlEqualTo("/category/categories"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        // Testar a rota com autenticação
        webTestClient.get()
                .uri("/category/categories")
                .header("Authorization", TEST_JWT)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void testCurrencyServiceRoute() {
        // Configurar mock do serviço de moeda
        stubFor(get(urlEqualTo("/currency/currencies"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        // Testar a rota com autenticação
        webTestClient.get()
                .uri("/currency/currencies")
                .header("Authorization", TEST_JWT)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
        }

    @Test
    void testFinanceServiceRoute() {
        // Configurar mock do serviço de finanças
        stubFor(get(urlEqualTo("/finance/transactions"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        // Testar a rota com autenticação
        webTestClient.get()
                .uri("/finance/transactions")
                .header("Authorization", TEST_JWT)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void testNotificationServiceRoute() {
        // Configurar mock do serviço de notificações
        stubFor(get(urlEqualTo("/notification/notifications"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        // Testar a rota com autenticação
        webTestClient.get()
                .uri("/notification/notifications")
                .header("Authorization", TEST_JWT)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test 
    void testUserServiceRoute() {
        // Configurar mock do serviço de usuário
        stubFor(get(urlEqualTo("/user/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        // Testar a rota com autenticação
        webTestClient.get()
                .uri("/user/users")
                .header("Authorization", TEST_JWT)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }


    @Test
    void testUnauthorizedAccessToBudgetService() {
        // Testar acesso sem token
        webTestClient.get()
                .uri("/budget/budgets")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testUnauthorizedAccessToCategoryService() {
        // Testar acesso sem token
        webTestClient.get()
                .uri("/category/categories")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testUnauthorizedAccessToCurrencyService() {
        // Testar acesso sem token
        webTestClient.get()
                .uri("/currency/currencies")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testUnauthorizedAccessToFinanceService() {
        // Testar acesso sem token
        webTestClient.get()
                .uri("/finance/transactions")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testUnauthorizedAccessToNotificationService() {
        // Testar acesso sem token
        webTestClient.get()
                .uri("/notification/notifications")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testUnauthorizedAccessToUserService() {
        // Testar acesso sem token
        webTestClient.get()
                .uri("/user/users")
                .exchange()
                .expectStatus().isUnauthorized();
    }
} 