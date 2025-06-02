package com.finan_control.api_gateway.config;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

/**
 * Configuração de segurança de teste para o API Gateway
 * 
 * Esta classe configura o SecurityWebFilterChain para testes, permitindo que
 * os endpoints sejam acessados sem autenticação.

 **/
@Configuration
@Profile("test")
public class TestSecurityConfig {

    @Bean
    @Primary
    public ReactiveJwtDecoder jwtDecoder() {
        /**
         * Decodificador de JWT de teste
         * 
         * Este método retorna um decodificador de JWT que simula um token JWT válido.
         * O token simulado não possui assinatura e é considerado válido por 1 hora.
         * 
        **/
        return token -> Mono.just(
            new Jwt(
                "token",                           
                Instant.now(),                     
                Instant.now().plusSeconds(3600),  
                Map.of("alg", "none"),           
                Map.of(                          
                    "sub", "test-user",
                    "roles", "ROLE_USER"
                )
            )
        );
    }

    @Bean
    @Primary
    public SecurityWebFilterChain testSecurityFilterChain(ServerHttpSecurity http) {
        /**
         * Filtro de segurança de teste
         * 
         * Este método configura o SecurityWebFilterChain para testes, permitindo que
         * os endpoints sejam acessados sem autenticação.
         * 
        **/
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/auth-fallback", 
                            "/budget-fallback",
                            "/category-fallback",
                            "/currency-fallback",
                            "/finance-fallback",
                            "/notification-fallback",
                            "/user-fallback").permitAll()
                .pathMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll()
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(resourceServer -> resourceServer
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(token -> 
                        Mono.just(new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(
                            token,
                            java.util.Collections.singletonList(
                                new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER")
                            )
                        ))
                    )
                )
            )
            .build();
    }
} 