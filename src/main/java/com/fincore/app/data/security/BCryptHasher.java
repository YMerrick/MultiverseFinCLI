package com.fincore.app.data.security;

import com.fincore.app.model.identity.PasswordHasher;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.security.SecureRandom;
import java.util.Arrays;

public class BCryptHasher implements PasswordHasher {
    private final SecureRandom saltRandomiser;

    public BCryptHasher(SecureRandom randomiser) {
        this.saltRandomiser = randomiser;
    }

    public BCryptHasher() {
        this(new SecureRandom());
    }

    @Override
    public String hash(char[] plain) {
        byte[] salt = new byte[16];
        saltRandomiser.nextBytes(salt);
        return OpenBSDBCrypt.generate(plain, salt, 12);
    }

    @Override
    public boolean verify(char[] plain, String hash) {
        return OpenBSDBCrypt.checkPassword(hash, plain);
    }
}
