package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.domain.shared.Money;
import com.fincore.app.domain.shared.MoneyFormatter;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.model.MenuResponseBuilder;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class displayBalanceAction implements MenuAction {
    private SessionManager sessionManager;
    private AccountService accountService;

    @Override
    public MenuResponse run(AuthContext ctx) {
        UUID accountId = sessionManager.validate(ctx.getSession()).orElseThrow().accId();
        Money amount = accountService.getBalance(accountId);
        String message = String.format(
                "Current balance: %s",
                MoneyFormatter.format(amount)
        );

        return new MenuResponseBuilder()
                .message(message)
                .build();
    }
}
