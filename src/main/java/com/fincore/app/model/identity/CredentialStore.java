package com.fincore.app.model.identity;

import java.util.Optional;

public interface CredentialStore {
    public Optional<Credentials> findByUsername(String username);
    public void save(Credentials cred);
}
