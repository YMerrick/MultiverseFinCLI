package com.fincore.app.domain.identity;

import java.util.Optional;

public interface CredentialRepo {
    Optional<Credentials> getByUsername(String username);
    void save(Credentials cred);
}
