package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.domain.account.Account;
import com.fincore.app.domain.identity.Session;
import com.fincore.app.domain.shared.AccountException;
import com.fincore.app.domain.shared.SessionException;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.menu.model.MenuResponseBuilder;
import com.fincore.app.presentation.cli.io.InputProvider;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class SelectAccountAction implements MenuAction {
    private AccountService accountService;
    private SessionManager sessionManager;
    private InputProvider inputProvider;

    @Override
    public MenuResponse run(AuthContext ctx) {
        List<Account> accountList;
        List<String> accountAsStringList;
        String message;
        int userChoice;
        Session currentSession;

        UUID userId;

        try {
            currentSession = sessionManager.validate(ctx.getSession())
                    .orElseThrow(
                            () -> new SessionException("Session does not exist")
                    );
        } catch (Exception e) {
            return new MenuResponseBuilder()
                    .message(e.getMessage())
                    .build();
        }
        userId = currentSession.userId();

        try {
            accountList = accountService.getAccounts(userId);
        } catch (AccountException e) {
            return new MenuResponseBuilder()
                    .message(e.getMessage())
                    .build();
        }

        accountAsStringList = accountList.stream().map(Account::repr).toList();
        message = IntStream.range(0, accountAsStringList.size())
                .mapToObj(i -> formatOptions(i+1, accountAsStringList.get(i)))
                .collect(Collectors.joining("\n\n"));

        userChoice = inputProvider.getIntInput(message + "\nSelect a number corresponding to the account: ");

        try {
            currentSession.setAccId(accountList.get(userChoice - 1).getId());
        } catch (IndexOutOfBoundsException e) {
            return new MenuResponseBuilder()
                    .message("You entered an invalid choice\nAborting...")
                    .build();
        }

        message = String.format(
                "You have selected\n%s",
                accountList.get(userChoice - 1).repr()
        );

        return new MenuResponseBuilder()
                .message(message)
                .build();
    }

    private String formatOptions(int number, String option) {
        return String.format(
                "%d.-------------\n%s",
                number,
                option
        );
    }
}
