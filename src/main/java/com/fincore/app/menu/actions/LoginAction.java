package com.fincore.app.menu.actions;

import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.domain.shared.AuthException;
import com.fincore.app.menu.model.*;
import com.fincore.app.presentation.cli.io.InputProvider;
import com.fincore.app.presentation.cli.io.PasswordReader;
import lombok.AllArgsConstructor;

import static com.fincore.app.menu.model.MenuDirective.*;

import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
public class LoginAction implements MenuAction {
    private InputProvider input;
    private PasswordReader passwordReader;
    private MenuGroup subMenu;
    private AuthService authService;
    private SessionManager sessionManager;

    @Override
    public MenuResponse run(AuthContext ctx) {
        String message;
        UUID sessionId;
        UUID accountId;

        String username = this.input.getStringInput("Username: ");
        char[] password;

        try {
            password = this.passwordReader.getPasswordInput("Password");
        } catch (Exception e) {
            password = this.input.getStringInput("Password").toCharArray();
        }

        try {
            accountId = authService.login(username, password);
            sessionId = sessionManager.issue(accountId);
            ctx.setSession(sessionId);
        } catch (AuthException e) {
            message = "Login has encountered an error";
            return new MenuResponseBuilder()
                    .directive(STAY)
                    .message(message)
                    .build();
        } catch (IllegalArgumentException e) {
            message = "An error occurred trying to issue a session";
            return new MenuResponseBuilder()
                    .directive(STAY)
                    .message(message)
                    .build();
        }

        message = "Login Successful";
        return new MenuResponseBuilder()
                .directive(GOTO_MENU)
                .submenu(subMenu)
                .message(message)
                .build();
    }
}
