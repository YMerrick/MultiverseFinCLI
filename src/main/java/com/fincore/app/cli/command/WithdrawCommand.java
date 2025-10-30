package com.fincore.app.cli.command;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.Context;
import com.fincore.app.application.auth.SessionManager;

import java.util.UUID;

public record WithdrawCommand(double amount, AccountService accService, SessionManager seshService) implements Command {
    @Override
    public void execute(Context ctx) {
        UUID accId = seshService.validate(ctx.getSession()).orElseThrow().accId();
        accService.withdraw(accId, amount);
    }
}
