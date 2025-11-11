package com.fincore.app.boot;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.accounts.UserService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.menu.model.MenuRenderer;

public record Services(
        AuthService authService,
        AccountService accountService,
        UserService userService,
        SessionManager sessionManager,
        AuthContext ctx,
        MenuRenderer renderer
) {
}
