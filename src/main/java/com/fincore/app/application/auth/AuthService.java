package com.fincore.app.application.auth;

import com.fincore.app.domain.identity.CredentialStore;
import com.fincore.app.domain.identity.Credentials;
import com.fincore.app.domain.identity.PasswordHasher;
import com.fincore.app.domain.shared.AuthException;

import java.util.Optional;
import java.util.UUID;

public class AuthService {
    private CredentialStore credsRepo;
    private PasswordHasher hasher;

    public AuthService(CredentialStore credsRepo, PasswordHasher hasher) {
        this.credsRepo = credsRepo;
        this.hasher = hasher;
    }

    public UUID login(String username, char[] password) throws AuthException {
        Optional<Credentials> credOrNull = getCredentials(username);
        if (credOrNull.isEmpty()) throw new AuthException("User does not exist");
        Credentials userCred = credOrNull.get();

        if (!hasher.verify(password, userCred.passwordHash())) throw new AuthException("Invalid credentials");

        return userCred.accId();
    }

    private Optional<Credentials> getCredentials(String username) {
        return credsRepo.getByUsername(username);
    }

    public void register(String username, char[] password, UUID accId) {
        if (getCredentials(username).isPresent()) throw new AuthException("Username already exists");
        String hashedPassword = hasher.hash(password);
        Credentials newCredentials = new Credentials(username, hashedPassword, accId);
        credsRepo.save(newCredentials);
    }
}
