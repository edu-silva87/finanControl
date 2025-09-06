package com.finan_control.auth_service.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenGeneratorTest {
   
    @Test
    @DisplayName("Should generate a non-null and non-empty token")
    void generateSafeToken_ShouldNotBeNullOrEmpty() {
        String token = TokenGenerator.generateSafeToken();
        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
    }

    @Test
    @DisplayName("Should generate a token with correct length for 9 bytes in Base64 URL-safe")
    void generateSafeToken_ShouldHaveExpectedLength() {
        String token = TokenGenerator.generateSafeToken();
        assertEquals(12, token.length(), "Token length should be 12 characters");
    }

    @Test
    @DisplayName("Should generate URL-safe Base64 token")
    void generateSafeToken_ShouldBeUrlSafeBase64() {
        String token = TokenGenerator.generateSafeToken();
        assertDoesNotThrow(() -> Base64.getUrlDecoder().decode(token), "Token should be valid Base64 URL-safe");
    }

    @Test
    @DisplayName("Should generate different tokens in multiple calls")
    void generateSafeToken_ShouldBeRandom() {
        Set<String> tokens = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            tokens.add(TokenGenerator.generateSafeToken());
        }
        assertEquals(100, tokens.size(), "Tokens should be unique across multiple generations");
    }
}
