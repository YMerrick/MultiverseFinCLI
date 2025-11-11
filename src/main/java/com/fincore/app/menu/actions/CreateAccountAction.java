package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.domain.account.AccountType;
import com.fincore.app.domain.shared.Money;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.model.MenuResponseBuilder;
import com.fincore.app.presentation.cli.io.InputProvider;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class CreateAccountAction implements MenuAction {
    private InputProvider inputProvider;
    private AccountService accountService;
    private SessionManager sessionManager;

    @Override
    public MenuResponse run(AuthContext ctx) {
        Money moneyBalance;
        AccountType accountType;

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

        UUID userId = sessionManager.validate(ctx.getSession()).orElseThrow().userId();

        // Get full name or don't pass account holder seeing as user id ties them together
        String accountHolder = "";

        accountService.register(accountHolder, userId, moneyBalance, accountType);

        return new MenuResponseBuilder().build();
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
