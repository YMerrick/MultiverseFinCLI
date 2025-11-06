package com.fincore.app.data.file;

import com.fincore.app.domain.identity.CredentialStore;
import com.fincore.app.domain.identity.Credentials;

import java.util.Optional;

public class CSVCredentialStore implements CredentialStore {
    @Override
    public Optional<Credentials> getByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void save(Credentials cred) {

    }
}
