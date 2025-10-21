package com.fincore.app.data.security;

import com.fincore.app.model.identity.PasswordHasher;

public class NoHasher implements PasswordHasher {

    @Override
    public String hash(char[] plain) {
        return String.valueOf(plain).trim();
    }

    @Override
    public boolean verify(char[] plain, String hashString) {
        String hashed = hash(plain);
        return hashed.contentEquals(hashString);
    }
}
