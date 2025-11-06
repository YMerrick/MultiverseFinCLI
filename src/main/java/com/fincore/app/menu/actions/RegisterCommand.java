package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.AuthContext;

import java.util.UUID;

public record RegisterCommand(
        AuthService authService,
        AccountService accService,
        String username,
        char[] password
) implements Command{
    @Override
    public void execute(AuthContext ctx) {
        UUID accId = UUID.randomUUID();
        accService.register(username, accId, 0);
        authService.register(username, password, accId);
    }
}
