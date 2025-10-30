package com.fincore.app.cli.command;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.Context;

import java.util.UUID;

public record RegisterCommand(
        AuthService authService,
        AccountService accService,
        String username,
        char[] password
) implements Command{
    @Override
    public void execute(Context ctx) {
        UUID accId = UUID.randomUUID();
        accService.register(username, accId, 0);
        authService.register(username, password, accId);
    }
}
