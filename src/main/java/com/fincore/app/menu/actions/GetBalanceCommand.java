package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.presentation.cli.io.IOHandler;
import com.fincore.app.domain.shared.Money;
import com.fincore.app.domain.shared.MoneyFormatter;

import java.util.UUID;

public record GetBalanceCommand(IOHandler io, AccountService service, SessionManager sessionManager) implements Command {
    @Override
    public void execute(AuthContext ctx) {
        UUID accId = sessionManager.validate(ctx.getSession()).orElseThrow().accId();
        Money balance = service.getBalance(accId);
        String formattedBalance = MoneyFormatter.format(balance);
        io.renderOutput(formattedBalance);
    }
}
