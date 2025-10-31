package com.fincore.app.model.identity;

import java.util.Optional;

public interface CredentialStore {
    Optional<Credentials> getByUsername(String username);
    void save(Credentials cred);
}
