package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.domain.shared.InsufficientFundsException;
import com.fincore.app.domain.shared.Money;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.model.MenuResponseBuilder;
import com.fincore.app.presentation.cli.io.InputProvider;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@AllArgsConstructor
public class WithdrawAction implements MenuAction {
    private SessionManager sessionManager;
    private AccountService accountService;
    private InputProvider inputProvider;

    @Override
    public MenuResponse run(AuthContext ctx) {
        String message = "Withdrawal Successful";
        UUID accountId = sessionManager.validate(ctx.getSession()).orElseThrow().accId();
        Currency currency = accountService.getBalance(accountId).getCurrency();
        double amount = inputProvider.getDoubleInput("Please enter the amount to withdraw: ");
        Money moneyAmount = Money.ofMajor(BigDecimal.valueOf(amount), currency);

        try {
            accountService.withdraw(accountId, moneyAmount);
        } catch (IllegalStateException | InsufficientFundsException e) {
            return new MenuResponseBuilder()
                    .message(e.getMessage())
                    .build();
        }

        return new MenuResponseBuilder()
                .message(message)
                .build();
    }
}
