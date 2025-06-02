package com.finan_control.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configuração de segurança para o API Gateway
 * 
 * Esta classe é responsável por configurar a segurança do API Gateway, incluindo:
 * - Configuração do filtro de segurança
 * - Configuração das regras de autorização
 * - Configuração da autenticação via JWT
 */

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        /*
         * Security Config 
         * - Desabilitar CSRF
         * - Permitir endpoints públicos (/auth/login, /auth/register)
         * - Permitir acesso ao actuator
         * - Exigir autenticação para outros endpoints
         * - Configurar o conversor JWT para extrair claims e autoridades
         */
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(HttpMethod.POST, "/auth/login","/auth/register").permitAll()  
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(resourceServer -> resourceServer
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtConverter())
                )
            )
            .build();
    }

    /*
     * JWT Converter
     * - Extrai o username do claim 'sub'
     * - Extrai as roles do claim 'roles'
     * - Define ROLE_USER como padrão se não houver roles
     * - Converte roles em GrantedAuthority
     * - Cria um JwtAuthenticationToken com as informações
     */
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtConverter() {
        return jwt -> {
            String username = jwt.getClaimAsString("sub");
            List<String> roles = jwt.getClaimAsStringList("roles");
            if (roles == null || roles.isEmpty()) {
                roles = Collections.singletonList("ROLE_USER");
            }
            
            Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
            
            return Mono.just(new JwtAuthenticationToken(jwt, authorities, username));
        };
    }
}