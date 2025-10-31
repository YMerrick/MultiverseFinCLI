package com.fincore.app.cli.command;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.cli.io.IOHandler;

// Needs to worry only about creating commands
// Does not contain fields pertaining to services
// Pass the services to the methods instead
public class CommandFactory {
    public static Command createTransactionCommand(
            double amount,
            AccountService accService,
            SessionManager sessionManager,
            String type
    ) {
        return switch (type) {
            case "DEPOSIT" -> new DepositCommand(amount, accService, sessionManager);
            case "WITHDRAW" -> new WithdrawCommand(amount, accService, sessionManager);
            case null, default -> throw new IllegalArgumentException();
        };
    }

    public static Command createGetBalance(IOHandler io, AccountService accService, SessionManager sessionManager) {
        return new GetBalanceCommand(io, accService, sessionManager);
    }

    public static Command createLogout(SessionManager sessionManager) {
        return new LogoutCommand(sessionManager);
    }

    public static Command createLogin(AuthService authService, SessionManager sessionManager, String username, char[] password) {
        return new LoginCommand(authService, sessionManager, username, password);
    }

    public static Command createRegister(
            AuthService authService,
            AccountService accountService,
            String username,
            char[] password
    ) {
        return new RegisterCommand(
                authService,
                accountService,
                username,
                password
        );
    }
}
