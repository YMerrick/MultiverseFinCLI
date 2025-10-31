package com.fincore.app.model.identity;

public interface PasswordHasher {
    String hash(char[] plain);
    boolean verify(char[] plain, String hash);
}
