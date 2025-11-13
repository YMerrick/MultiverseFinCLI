package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.domain.account.Account;
import com.fincore.app.domain.shared.AccountException;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.model.MenuResponseBuilder;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DisplayAccountAction implements MenuAction {
    private AccountService accountService;
    private SessionManager sessionManager;

    @Override
    public MenuResponse run(AuthContext ctx) {
        UUID userId = sessionManager.validate(ctx.getSession()).orElseThrow().userId();
        List<Account> accountList;
        String message;

        try {
            accountList = accountService.getAccounts(userId);
        } catch (AccountException e) {
            return new MenuResponseBuilder()
                    .message(e.getMessage())
                    .build();
        }

        message = accountList.stream().map(Account::repr).collect(Collectors.joining("\n\n"));

        return new MenuResponseBuilder()
                .message(message)
                .build();
    }
}
