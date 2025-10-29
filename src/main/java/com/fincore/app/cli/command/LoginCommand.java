package com.fincore.app.cli.command;

import com.fincore.app.application.auth.AuthService;

import java.util.UUID;

public record LoginCommand(AuthService service, String username, char[] password) implements Command{
    @Override
    public void execute() {
        UUID accId = service.login(username, password);
        // Create session

    }
}
