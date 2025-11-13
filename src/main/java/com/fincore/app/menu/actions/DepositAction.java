package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.domain.identity.Session;
import com.fincore.app.domain.shared.AccountException;
import com.fincore.app.domain.shared.Money;
import com.fincore.app.domain.shared.SessionException;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuDirective;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.model.MenuResponseBuilder;
import com.fincore.app.presentation.cli.io.InputProvider;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class DepositAction implements MenuAction {
    private SessionManager sessionManager;
    private AccountService accountService;
    private InputProvider inputProvider;

    @Override
    public MenuResponse run(AuthContext ctx) {
        UUID accountId;

        try {
            accountId = sessionManager
                    .validate(ctx.getSession())
                    .orElseThrow(() -> new SessionException("Session does not exist"))
                    .accId();
        } catch (SessionException e) {
            return new MenuResponseBuilder()
                    .message(e.getMessage())
                    .build();
        }

        if (Objects.isNull(accountId))
            return new MenuResponseBuilder()
                .message("Account has not been selected yet")
                .build();

        Currency currency = accountService.getBalance(accountId).getCurrency();
        double depositingAmount = inputProvider.getDoubleInput("Enter the amount to deposit: ");

        Money moneyAmount = Money.ofMajor(BigDecimal.valueOf(depositingAmount), currency);

        try {
            accountService.deposit(accountId, moneyAmount);
        } catch (IllegalStateException e) {
            return new MenuResponseBuilder()
                    .message(e.getMessage())
                    .build();
        }

        return new MenuResponseBuilder()
                .message("Deposit Successful")
                .build();
    }
}
