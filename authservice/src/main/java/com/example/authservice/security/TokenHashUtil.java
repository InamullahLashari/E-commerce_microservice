package com.example.authservice.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class TokenHashUtil {

    public static String hash(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error hashing token", e);
        }
    }
}