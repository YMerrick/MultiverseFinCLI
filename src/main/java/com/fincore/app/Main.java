package com.fincore.app;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.cli.command.CommandHandler;
import com.fincore.app.cli.app.MenuController;
import com.fincore.app.cli.io.*;
import com.fincore.app.cli.menu.LoginItem;
import com.fincore.app.cli.menu.Menu;
import com.fincore.app.cli.menu.MenuDirective;
import com.fincore.app.data.inmemory.*;
import com.fincore.app.data.security.*;
import com.fincore.app.model.account.Account;
import com.fincore.app.cli.menu.MenuItem;
import com.fincore.app.model.account.AccountStore;
import com.fincore.app.model.identity.CredentialStore;
import com.fincore.app.model.identity.PasswordHasher;
import com.fincore.app.model.identity.SessionStore;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Account user = new Account(UUID.randomUUID(), "TestUser",10_000);
        IOHandler io = new NumberedIO(System.out, System.in);

        CommandHandler controller = getCommandHandler(io);

        MenuItem register = MenuItem.builder()
                .label("Register")
                .directive(MenuDirective.STAY)
                .command(controller::handleRegister)
                .build();

        MenuItem exit = MenuItem.builder()
                .label("Exit")
                .directive(MenuDirective.EXIT)
                .build();

        MenuItem back = MenuItem.builder()
                .label("Back")
                .directive(MenuDirective.BACK)
                .build();

        MenuItem logout = MenuItem.builder()
                .label("Logout")
                .directive(MenuDirective.BACK)
                .command(controller::handleLogout)
                .build();

        MenuItem deposit = MenuItem.builder()
                .label("Deposit")
                .directive(MenuDirective.STAY)
                .command(controller::handleDeposit)
                .build();

        MenuItem withdraw = MenuItem.builder()
                .label("Withdraw")
                .directive(MenuDirective.STAY)
                .command(controller::handleWithdraw)
                .build();

        MenuItem getBalance = MenuItem.builder()
                .label("Get Balance")
                .directive(MenuDirective.STAY)
                .command(controller::handleGetBalance)
                .build();


        Menu accountMenu = new Menu(
                "Account",
                io
        );
        MenuItem goToAccMenu = MenuItem.builder()
                .subMenu(accountMenu)
                .label("Accounts")
                .directive(MenuDirective.GOTO_MENU)
                .build();
        accountMenu.addMenuItem(back);
        accountMenu.addMenuItem(deposit);
        accountMenu.addMenuItem(withdraw);
        accountMenu.addMenuItem(getBalance);


        Menu mainMenu = new Menu(
                "Main Menu",
                io
        );
        mainMenu.addMenuItem(logout);
        MenuItem login = new LoginItem(
                MenuDirective.GOTO_MENU,
                "Login",
                mainMenu,
                controller::handleLogin
        );
        mainMenu.addMenuItem(goToAccMenu);

        Menu authMenu = new Menu(
                "User",
                io
        );
        authMenu.addMenuItem(exit);
        authMenu.addMenuItem(login);
        authMenu.addMenuItem(register);

        MenuController menuRunner = new MenuController(authMenu);
        menuRunner.start();

    }

    private static CommandHandler getCommandHandler(IOHandler io) {
        SessionStore sessionStore = new InMemorySessionStore();
        AccountStore accountStore = new InMemoryAccountStore();
        CredentialStore credRepo = new InMemoryCredentialStore();
        PasswordHasher hasher = new BCryptHasher();

        SessionManager sessionManager = new SessionManager(sessionStore);
        AccountService accService = new AccountService(accountStore);
        AuthService authService = new AuthService(credRepo, hasher);

        return new CommandHandler(sessionManager, accService, io, authService);
    }
}