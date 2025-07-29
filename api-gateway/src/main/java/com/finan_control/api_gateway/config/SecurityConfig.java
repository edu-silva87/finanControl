package com.finan_control.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    final ReactiveClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(HttpMethod.POST, "/login").permitAll()
                .pathMatchers("/login").denyAll()
                .anyExchange().authenticated()
            )

            .oauth2Login(oauth2 -> oauth2
                .authorizationRequestResolver(authorizationRequestResolver(clientRegistrationRepository))
                .authenticationSuccessHandler(
                    new RedirectServerAuthenticationSuccessHandler(
                        "http://localhost:3000/profile"
                    )
                )
            )
            .csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

    private ServerOAuth2AuthorizationRequestResolver authorizationRequestResolver(
            ReactiveClientRegistrationRepository clientRegistrationRepository2) {
                
        var authorizationRequestResolver = new DefaultServerOAuth2AuthorizationRequestResolver(clientRegistrationRepository2);

        authorizationRequestResolver
            .setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());

        return authorizationRequestResolver;
    } 
    
    
}