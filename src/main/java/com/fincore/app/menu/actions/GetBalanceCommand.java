package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.Context;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.presentation.cli.io.IOHandler;
import com.fincore.app.model.shared.Money;
import com.fincore.app.model.shared.MoneyFormatter;

import java.util.UUID;

public record GetBalanceCommand(IOHandler io, AccountService service, SessionManager sessionManager) implements Command {
    @Override
    public void execute(Context ctx) {
        UUID accId = sessionManager.validate(ctx.getSession()).orElseThrow().accId();
        Money balance = service.getBalance(accId);
        String formattedBalance = MoneyFormatter.format(balance);
        io.renderOutput(formattedBalance);
    }
}
