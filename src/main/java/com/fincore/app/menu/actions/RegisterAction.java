package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.domain.shared.AuthException;
import com.fincore.app.domain.shared.Money;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.model.MenuResponseBuilder;
import com.fincore.app.presentation.cli.io.InputProvider;
import com.fincore.app.presentation.cli.io.PasswordReader;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
public class RegisterAction implements MenuAction {
    private AuthService authService;
    private AccountService accountService;
    private InputProvider inputProvider;
    private PasswordReader passwordReader;

    @Override
    public MenuResponse run(AuthContext ctx) {
        String message = "Registration Successful";
        String username;
        char[] password;
        UUID accId;
        String currencyCode;
        Money moneyBalance;

        username = inputProvider.getStringInput("Username: ");
        try {
            password = passwordReader.getPasswordInput("Password: ");
        } catch (Exception ignored) {
            password = inputProvider.getStringInput("\rPassword: ").toCharArray();
        }

        accId = UUID.randomUUID();

        try {
            authService.register(username, password, accId);
        } catch (AuthException e) {
            message = e.getMessage();
        }
        double initialBalanceAsDouble = inputProvider.getDoubleInput("Starting balance: ");
        currencyCode = inputProvider.getStringInput("Currency Code: ").toUpperCase();

        try {
            moneyBalance = Money.ofMajor(BigDecimal.valueOf(initialBalanceAsDouble), currencyCode);
        } catch (IllegalArgumentException e) {
            return new MenuResponseBuilder()
                    .message("Could not find currency code\nAborting registration")
                    .build();
        }

        accountService.register(username, accId, moneyBalance);

        return new MenuResponseBuilder()
                .message(message)
                .build();
    }
}
