package com.fincore.app.cli.command;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.Context;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.cli.io.IOHandler;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CommandHandler {
    // Find a better way to retrieve session ID
    private SessionManager sessionManager;
    private AccountService accService;
    private IOHandler io;
    private AuthService authService;

    public void handleDeposit(Context ctx) {
        // Get user input
        double amount = 0.0;
        Command depositCommand = CommandFactory.createTransactionCommand(
                amount,
                accService,
                sessionManager,
                "DEPOSIT"
        );
        depositCommand.execute(ctx);
        // Log command here
    }

    public void handleWithdraw(Context ctx) {
        double amount = 0.0;
        Command withdrawCommand = CommandFactory.createTransactionCommand(
                amount,
                accService,
                sessionManager,
                "WITHDRAW"
        );
        withdrawCommand.execute(ctx);
        // Log command here
    }

    public void handleGetBalance(Context ctx) {
        Command getBalance = CommandFactory.createGetBalance(io, accService, sessionManager);
        getBalance.execute(ctx);
        // Log command here
    }

    public void handleLogout(Context ctx) {
        Command logout = CommandFactory.createLogout(sessionManager);
        logout.execute(ctx);
    }

    public void handleLogin(Context ctx) {
        String username = io.getInput("Username: ");
        char[] password;
        try {
            password = io.getPasswordInput("Password: ");
        } catch (NullPointerException ignored) {
            password = io.getInput("\rPassword: ").toCharArray();
        }
        Command login = CommandFactory.createLogin(authService, sessionManager, username, password);
        io.renderSpaces();
        login.execute(ctx);
    }

    public void handleRegister(Context ctx) {
        String username = io.getInput("Username: ");
        char[] password;
        try {
            password = io.getPasswordInput("Password: ");
        } catch (NullPointerException ignored) {
            password = io.getInput("\rPassword: ").toCharArray();
        }
        Command register = CommandFactory.createRegister(
                authService,
                accService,
                username,
                password
        );
        register.execute(ctx);
        io.renderSpaces();
    }
}
