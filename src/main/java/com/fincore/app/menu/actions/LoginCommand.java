package com.fincore.app.menu.actions;

import com.fincore.app.application.auth.*;

import java.util.UUID;

public record LoginCommand(AuthService authService, SessionManager seshService, String username, char[] password) implements Command{
    @Override
    public void execute(Context ctx) {
        UUID accId = authService.login(username, password);
        UUID sessionId = seshService.issue(accId);
        ctx.setSession(sessionId);
    }
}
