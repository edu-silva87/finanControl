package com.finan_control.api_gateway.unit.config;

import com.finan_control.api_gateway.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;

import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockMakers;

/**
 * Testes unitários para a configuração de segurança do API Gateway
 * 
 * Esta classe testa a configuração de segurança do API Gateway, incluindo:
 * - Configuração do SecurityWebFilterChain
 * - Conversão e validação de tokens JWT
 * - Extração de claims e autoridades do JWT
 * - Regras de autorização para diferentes endpoints
 * 
 * Os testes utilizam mocks para simular o comportamento do Spring Security
 * e validam se as configurações estão sendo aplicadas corretamente.
 */


class SecurityConfigTest {

    /**
     * Configuração do SecurityConfig
     * 
     * Este campo armazena a instância do SecurityConfig que será testada.
     */
    private SecurityConfig securityConfig;
    private ServerHttpSecurity http;
    private ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec;
    private ServerHttpSecurity.AuthorizeExchangeSpec.Access accessSpec;
    private OAuth2ResourceServerSpec oauth2Spec;
    private ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec jwtSpec;

    /**
     * Configuração do mock do ServerHttpSecurity
     * 
     * Este método configura o mock do ServerHttpSecurity para retornar o próprio objeto em cada chamada.
     */
    @BeforeEach
    void setUp() {
        /**
         * Configuração inicial dos testes
         * 
         * Este método é executado antes de cada teste e configura o ambiente necessário
         * para testar a segurança do API Gateway.
         *
         * Responsabilidades:
         * - Inicialização dos componentes principais
         * - Configuração dos mocks do Spring Security
         * - Definição do comportamento dos mocks
         *
         * Componentes inicializados:
         * - SecurityConfig: Classe principal sendo testada
         * - ServerHttpSecurity: Mock para configurações de segurança HTTP
         * - AuthorizeExchangeSpec: Mock para regras de autorização
         * - OAuth2ResourceServerSpec: Mock para configuração OAuth2
         * - JwtSpec: Mock para configuração JWT
         *
         * Comportamentos configurados:
         * - Retorno em cadeia dos mocks para permitir chamadas fluentes
         * - Simulação das configurações de segurança
         * - Captura de chamadas para verificação
         */
       
        securityConfig = new SecurityConfig();
        http = mock(ServerHttpSecurity.class, withSettings().mockMaker(MockMakers.SUBCLASS));
        authorizeExchangeSpec = mock(ServerHttpSecurity.AuthorizeExchangeSpec.class);
        accessSpec = mock(ServerHttpSecurity.AuthorizeExchangeSpec.Access.class);
        oauth2Spec = mock(OAuth2ResourceServerSpec.class);
        jwtSpec = mock(ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec.class);

        // Configurar o mock do ServerHttpSecurity para retornar o próprio objeto em cada chamada
        when(http.csrf(any())).thenReturn(http);
        when(http.authorizeExchange(any(Customizer.class))).thenAnswer(invocation -> {
            Customizer<ServerHttpSecurity.AuthorizeExchangeSpec> customizer = invocation.getArgument(0);
            customizer.customize(authorizeExchangeSpec);
            return http;
        });
        when(http.oauth2ResourceServer(any(Customizer.class))).thenReturn(http);

        // Configurar o mock do AuthorizeExchangeSpec
        when(authorizeExchangeSpec.pathMatchers(any(HttpMethod.class), anyString())).thenReturn(accessSpec);
        when(authorizeExchangeSpec.pathMatchers(anyString())).thenReturn(accessSpec);
        when(authorizeExchangeSpec.anyExchange()).thenReturn(accessSpec);
        when(accessSpec.permitAll()).thenReturn(authorizeExchangeSpec);
        when(accessSpec.authenticated()).thenReturn(authorizeExchangeSpec);

        // Configurar o mock do OAuth2ResourceServer
        when(oauth2Spec.jwt(any())).thenReturn(oauth2Spec);
        when(jwtSpec.jwtAuthenticationConverter(any())).thenReturn(jwtSpec);

        when(http.build()).thenReturn(mock(SecurityWebFilterChain.class));
    }

    @Test
    void testJwtConverterExtractsUsername() {
        // Criar um JWT de teste
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("sub", "testUser")
            .claim("roles", Collections.singletonList("USER"))
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build();

        try {
            // Obter o método jwtConverter através de reflexão
            var jwtConverterMethod = SecurityConfig.class.getDeclaredMethod("jwtConverter");
            jwtConverterMethod.setAccessible(true);

            // Invocar o método para obter o conversor
            @SuppressWarnings("unchecked")
            var converter = (Converter<Jwt, Mono<AbstractAuthenticationToken>>) 
                jwtConverterMethod.invoke(securityConfig);
            
            // Converter o JWT
            var result = converter.convert(jwt);

            // Verificar o resultado
            StepVerifier.create(result)
                .assertNext(token -> {
                    assertEquals("testUser", token.getName());
                    assertTrue(token.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
                })
                .verifyComplete();
        } catch (Exception e) {
            fail("Falha ao converter JWT: " + e.getMessage());
        }
    }

    @Test
    void testJwtConverterHandlesEmptyRoles() {
        // Criar um JWT sem roles
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("sub", "testUser")
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build();

        try {
            // Obter o método jwtConverter através de reflexão
            var jwtConverterMethod = SecurityConfig.class.getDeclaredMethod("jwtConverter");
            jwtConverterMethod.setAccessible(true);

            // Invocar o método para obter o conversor
            @SuppressWarnings("unchecked")
            var converter = (Converter<Jwt, Mono<AbstractAuthenticationToken>>) 
                jwtConverterMethod.invoke(securityConfig);
            
            // Converter o JWT
            var result = converter.convert(jwt);

            // Verificar se role padrão foi aplicada
            StepVerifier.create(result)
                .assertNext(token -> {
                    assertTrue(token.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
                })
                .verifyComplete();
        } catch (Exception e) {
            fail("Falha ao converter JWT: " + e.getMessage());
        }
    }

    @Test
    void testJwtConverterHandlesCustomRoles() {
        // Criar um JWT com múltiplas roles
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("sub", "testUser")
            .claim("roles", java.util.Arrays.asList("ADMIN", "MANAGER"))
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build();

        try {
            // Obter o método jwtConverter através de reflexão
            var jwtConverterMethod = SecurityConfig.class.getDeclaredMethod("jwtConverter");
            jwtConverterMethod.setAccessible(true);

            // Invocar o método para obter o conversor
            @SuppressWarnings("unchecked")
            var converter = (Converter<Jwt, Mono<AbstractAuthenticationToken>>) 
                jwtConverterMethod.invoke(securityConfig);
            
            // Converter o JWT
            var result = converter.convert(jwt);

            // Verificar se todas as roles foram convertidas corretamente
            StepVerifier.create(result)
                .assertNext(token -> {
                    assertTrue(token.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
                    assertTrue(token.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER")));
                })
                .verifyComplete();
        } catch (Exception e) {
            fail("Falha ao converter JWT: " + e.getMessage());
        }
    }

    @Test
    void testSecurityFilterChainConfiguration() {
        // Testar a criação do SecurityWebFilterChain
        SecurityWebFilterChain filterChain = securityConfig.securityWebFilterChain(http);

        // Verificar configuração do CSRF
        verify(http).csrf(any());

        // Verificar configuração do authorizeExchange usando o novo Lambda DSL
        verify(http).authorizeExchange(any(Customizer.class));
        verify(authorizeExchangeSpec).pathMatchers(eq(HttpMethod.POST), eq("/auth/login"));
        verify(authorizeExchangeSpec).pathMatchers(eq(HttpMethod.POST), eq("/auth/register"));
        verify(authorizeExchangeSpec).pathMatchers(eq("/actuator/**"));
        verify(authorizeExchangeSpec).anyExchange();
        verify(accessSpec, times(3)).permitAll();
        verify(accessSpec).authenticated();

        // Verificar configuração do OAuth2 usando o novo Lambda DSL
        verify(http).oauth2ResourceServer(any(Customizer.class));

        assertNotNull(filterChain, "SecurityWebFilterChain não deve ser nulo");
    }
} 