package com.finan_control.auth_service.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;

public final class TokenGenerator {
    private static final int BYTE_SIZE = 9;

    public static String generateSafeToken() {
        SecureRandom random = new SecureRandom();

        byte[] bytes = new byte[BYTE_SIZE];
        random.nextBytes(bytes);

        Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}