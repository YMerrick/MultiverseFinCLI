package com.fincore.app.boot;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.data.inmemory.InMemoryAccountStore;
import com.fincore.app.data.inmemory.InMemoryCredentialStore;
import com.fincore.app.data.inmemory.InMemorySessionStore;
import com.fincore.app.data.security.BCryptHasher;
import com.fincore.app.domain.account.AccountStore;
import com.fincore.app.domain.identity.CredentialStore;
import com.fincore.app.domain.identity.PasswordHasher;
import com.fincore.app.domain.identity.SessionStore;
import com.fincore.app.menu.actions.CommandFactory;
import com.fincore.app.menu.model.MenuGroup;
import com.fincore.app.menu.nav.MenuNavigator;
import com.fincore.app.presentation.cli.io.CliIO;
import com.fincore.app.presentation.cli.port.CliMenuRenderer;

public class ServicesFactory {
    public Services create() {
        AccountStore accountStore = new InMemoryAccountStore();
        CredentialStore credentialStore = new InMemoryCredentialStore();
        SessionStore sessionStore = new InMemorySessionStore();
        PasswordHasher passwordHasher = new BCryptHasher();

        // Application layer
        AuthService authService = new AuthService(credentialStore, passwordHasher);
        SessionManager sessionManager = new SessionManager(sessionStore);
        AccountService accountService = new AccountService(accountStore);

        AuthContext authContext = new AuthContext();

        // Presentation / Menu
        CliIO io = new CliIO(System.in, System.out);
        CommandFactory actionFactory = new CommandFactory(
                io,
                io,
                authService,
                sessionManager,
                accountService
        );

        var renderer = new CliMenuRenderer();
        MenuGroup root = new MenuBuilder().build(actionFactory);
        var nav = new MenuNavigator(root, renderer);

        return new Services(
                authService,
                accountService,
                sessionManager,
                authContext,
                nav,
                io
        );
    }
}
