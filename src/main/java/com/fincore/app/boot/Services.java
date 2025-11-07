package com.fincore.app.boot;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.menu.nav.MenuNavigator;
import com.fincore.app.presentation.cli.io.CliIO;

public record Services(
        AuthService authService,
        AccountService accountService,
        SessionManager sessionManager,
        AuthContext ctx,
        MenuNavigator navigator,
        CliIO io
) {
}
