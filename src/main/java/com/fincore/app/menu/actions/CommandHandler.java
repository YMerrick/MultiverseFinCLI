package com.fincore.app.menu.actions;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.*;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.presentation.cli.io.IOHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandHandler {
    private SessionManager sessionManager;
    private AccountService accService;
    private IOHandler io;
    private AuthService authService;

//    public MenuResponse handleDeposit(AuthContext ctx) {
//        // Get user input
//        String line;
//        double amount = -1;
//        MenuAction action = ;
//
//        do {
//            line = io.getInput("Please enter the amount you would like to deposit: ");
//            try {
//                amount = Double.parseDouble(line);
//            } catch (NumberFormatException ignored) {}
//        } while (amount < 0.0);
//        action.run(ctx);
//        // Log command here
//    }

//    public void handleWithdraw(AuthContext ctx) {
//        double amount = 0.0;
//        Command withdrawCommand = CommandFactory.createTransactionCommand(
//                amount,
//                accService,
//                sessionManager,
//                TransactionType.WITHDRAW
//        );
//        withdrawCommand.execute(ctx);
//        // Log command here
//    }
//
//    public MenuResponse handleGetBalance(AuthContext ctx) {
//        MenuAction getBalance = CommandFactory.createDisplayBalance(sessionManager, accService);
//        MenuResponse res = getBalance.run(ctx);
//        // Log command here
//
//        return res;
//    }

//    public void handleLogout(AuthContext ctx) {
//        Command logout = CommandFactory.createLogout(sessionManager);
//        logout.execute(ctx);
//    }
//
//    public void handleLogin(AuthContext ctx) {
//        String username = io.getInput("Username: ");
//        char[] password;
//        try {
//            password = io.getPasswordInput("Password: ");
//        } catch (NullPointerException ignored) {
//            password = io.getInput("\rPassword: ").toCharArray();
//        }
//        Command login = CommandFactory.createLogin(authService, sessionManager, username, password);
//        io.renderSpaces();
//        login.execute(ctx);
//    }

//    public void handleRegister(AuthContext ctx) {
//        String username = io.getInput("Username: ");
//        char[] password;
//        try {
//            password = io.getPasswordInput("Password: ");
//        } catch (NullPointerException ignored) {
//            password = io.getInput("\rPassword: ").toCharArray();
//        }
//        Command register = CommandFactory.createRegister(
//                authService,
//                accService,
//                username,
//                password
//        );
//        register.execute(ctx);
//        io.renderSpaces();
//    }
}
