package com.fincore.app.model.identity;

public interface PasswordHasher {
    public String hash(char[] plain);
    public boolean verify(char[] plain, String hash);
}
