package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.UserService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.domain.shared.AuthException;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.model.MenuResponseBuilder;
import com.fincore.app.presentation.cli.io.InputProvider;
import com.fincore.app.presentation.cli.io.PasswordReader;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class RegisterAction implements MenuAction {
    private AuthService authService;
    private UserService userService;
    private InputProvider inputProvider;
    private PasswordReader passwordReader;

    @Override
    public MenuResponse run(AuthContext ctx) {
        String message = "Registration Successful";
        String username;
        char[] password;
        UUID accId;
        String[] fullName;

        username = inputProvider.getStringInput("Username: ");
        password = getPassword();

        accId = UUID.randomUUID();
        try {
            registerAuthService(username, password, accId);
        }  catch (AuthException e) {
            return new MenuResponseBuilder()
                    .message(e.getMessage())
                    .build();
        }

        fullName = getFullName();

        userService.register(fullName[0], fullName[1]);

        return new MenuResponseBuilder()
                .message(message)
                .build();
    }

    private char[] getPassword() {
        try {
            return passwordReader.getPasswordInput("Password: ");
        } catch (Exception ignored) {
            return inputProvider.getStringInput("\rPassword: ").toCharArray();
        }
    }

    private void registerAuthService(String username, char[] password, UUID accId) {
        authService.register(username, password, accId);
    }

    private String[] getFullName() {
        String[] fullName = new String[2];
        fullName[0] = inputProvider.getStringInput("First name: ");
        fullName[1] = inputProvider.getStringInput("Last name: ");
        return fullName;
    }
}
