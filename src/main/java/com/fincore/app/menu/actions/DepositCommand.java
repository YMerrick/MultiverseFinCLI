package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.Context;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.domain.shared.Money;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositCommand(double amount, AccountService serviceHandler, SessionManager sessionManager) implements Command {
    @Override
    public void execute(Context ctx) {
        UUID accId = sessionManager.validate(ctx.getSession()).orElseThrow().accId();
        Money moneyAmount = Money.ofMajor(BigDecimal.valueOf(amount), "GBP");
        serviceHandler.deposit(accId, moneyAmount);
    }
}
