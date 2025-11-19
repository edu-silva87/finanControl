package com.finanControl.auth_service.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyLoader {

    public static RSAPublicKey loadPublicKey(InputStream in) throws Exception {
        String key = readKey(in);
        byte[] decoded = Base64.getDecoder().decode(key);
        return (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
    }

    public static RSAPrivateKey loadPrivateKey(InputStream in) throws Exception {
        String key = readKey(in);
        byte[] decoded = Base64.getDecoder().decode(key);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    private static String readKey(InputStream in) throws Exception {
        return new BufferedReader(new InputStreamReader(in))
                .lines()
                .filter(l -> !l.startsWith("-----"))
                .reduce("", (a, b) -> a + b);
    }
}

