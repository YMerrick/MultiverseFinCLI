package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.domain.account.AccountType;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        Money moneyBalance;
        AccountType accountType;

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

        try {
            moneyBalance = getBalance();
        } catch (IllegalArgumentException e) {
            return new MenuResponseBuilder()
                    .message("Could not find currency code\nAborting registration")
                    .build();
        }

        try {
            accountType = getAccountType();
        } catch (IndexOutOfBoundsException e) {
            return new MenuResponseBuilder()
                    .message("Invalid Account selection made\nAborting...")
                    .build();
        }

        accountService.register(username, accId, moneyBalance, accountType);

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

    private Money getBalance() {
        double initialBalanceAsDouble = inputProvider.getDoubleInput("Starting balance: ");
        String currencyCode = inputProvider.getStringInput("Currency Code: ").toUpperCase();
        return Money.ofMajor(BigDecimal.valueOf(initialBalanceAsDouble), currencyCode);
    }

    private AccountType getAccountType() {
        String prompt = IntStream.range(0, AccountType.values().length)
                .mapToObj(i -> i+1 + ". " + AccountType.values()[i])
                .collect(Collectors.joining("\n"));
        prompt += "\nPlease enter a number to select account type: ";
        return AccountType.values()[inputProvider.getIntInput(prompt) - 1];
    }
}
