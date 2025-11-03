package com.fincore.app.domain.identity;

public interface PasswordHasher {
    String hash(char[] plain);
    boolean verify(char[] plain, String hash);
}
