package com.finanControl.auth_service.config;

import com.finanControl.auth_service.utils.KeyLoader;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtConfig {

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        RSAKey rsaKey = loadRSAKey();
        var jwks = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwks);
    }

    private RSAKey loadRSAKey() throws Exception {
        var publicStream = getClass().getClassLoader().getResourceAsStream("public.pem");
        var privateStream = getClass().getClassLoader().getResourceAsStream("private.pem");

        var publicKey = KeyLoader.loadPublicKey(publicStream);
        var privateKey = KeyLoader.loadPrivateKey(privateStream);

        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();
    }
}

