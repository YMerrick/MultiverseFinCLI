package com.fincore.app.boot;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.accounts.UserService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.data.inmemory.InMemoryAccountRepo;
import com.fincore.app.data.inmemory.InMemoryCredentialRepo;
import com.fincore.app.data.inmemory.InMemorySessionStore;
import com.fincore.app.data.inmemory.InMemoryUserRepo;
import com.fincore.app.data.security.BCryptHasher;
import com.fincore.app.domain.account.AccountRepo;
import com.fincore.app.domain.identity.CredentialRepo;
import com.fincore.app.domain.identity.PasswordHasher;
import com.fincore.app.domain.identity.SessionStore;
import com.fincore.app.domain.user.User;
import com.fincore.app.domain.user.UserRepo;
import com.fincore.app.presentation.cli.port.CliMenuRenderer;

public class ServicesFactory {
    public Services create() {
        AccountRepo accountRepo = new InMemoryAccountRepo();
        CredentialRepo credentialRepo = new InMemoryCredentialRepo();
        SessionStore sessionStore = new InMemorySessionStore();
        PasswordHasher passwordHasher = new BCryptHasher();
        UserRepo userRepo = new InMemoryUserRepo();

        // Application layer
        AuthService authService = new AuthService(credentialRepo, passwordHasher);
        SessionManager sessionManager = new SessionManager(sessionStore);
        AccountService accountService = new AccountService(accountRepo);
        UserService userService = new UserService(userRepo);

        AuthContext authContext = new AuthContext();

        var renderer = new CliMenuRenderer();

        return new Services(
                authService,
                accountService,
                userService,
                sessionManager,
                authContext,
                renderer
        );
    }
}
