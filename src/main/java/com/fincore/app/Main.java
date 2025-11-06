package com.fincore.app;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.AuthService;
import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.menu.actions.CommandFactory;
import com.fincore.app.menu.actions.CommandHandler;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.nav.MenuNavigator;
import com.fincore.app.menu.model.MenuGroup;
import com.fincore.app.data.inmemory.*;
import com.fincore.app.data.security.*;
import com.fincore.app.menu.model.MenuItem;
import com.fincore.app.domain.account.AccountStore;
import com.fincore.app.domain.identity.CredentialStore;
import com.fincore.app.domain.identity.PasswordHasher;
import com.fincore.app.domain.identity.SessionStore;
import com.fincore.app.presentation.cli.io.CliIO;
import com.fincore.app.presentation.cli.io.NumberedIO;
import com.fincore.app.presentation.cli.port.CliMenuRenderer;
import com.fincore.app.presentation.cli.io.IOHandler;

import static com.fincore.app.menu.model.MenuDirective.*;

public class Main {
    public static void main(String[] args) {
        CommandFactory actionFactory = getActionFactory();
        CommandHandler handler = getCommandHandler(new NumberedIO(System.out, System.in));

        MenuItem exit = new MenuItem(
                "Exit",
                actionFactory.createTraversal(
                        EXIT,
                        null,
                        null
                )
        );

        MenuItem back = new MenuItem(
                "Back",
                actionFactory.createTraversal(
                        BACK,
                        null,
                        null
                )
        );

        MenuItem logout = new MenuItem(
                "Logout",
                actionFactory.createLogout()
        );

        MenuItem register = new MenuItem(
                "Register",
                actionFactory.createRegister()
        );

        MenuItem deposit = new MenuItem(
                "Deposit",
                actionFactory.createDeposit()
        );

        MenuItem withdraw = new MenuItem(
                "Withdraw",
                actionFactory.createWithdraw()
        );

        MenuItem displayBalance = new MenuItem(
                "Display Balance",
                actionFactory.createDisplayBalance()
        );

        MenuGroup accountMenuGroup = new MenuGroup("Account");
        MenuItem goToAccMenu = new MenuItem(
                "Accounts",
                actionFactory.createTraversal(
                        GOTO_MENU,
                        accountMenuGroup,
                        null
                )
        );

        accountMenuGroup.addMenuItem(back);
        accountMenuGroup.addMenuItem(deposit);
        accountMenuGroup.addMenuItem(withdraw);
        accountMenuGroup.addMenuItem(displayBalance);


        MenuGroup mainMenuGroup = new MenuGroup("Main Menu");
        mainMenuGroup.addMenuItem(logout);
        mainMenuGroup.addMenuItem(goToAccMenu);

        MenuGroup authMenuGroup = new MenuGroup("User");

        MenuAction loginAction = actionFactory.createLogin(mainMenuGroup);

        MenuItem login = new MenuItem(
                "Login",
                loginAction
        );

        authMenuGroup.addMenuItem(exit);
        authMenuGroup.addMenuItem(login);
        authMenuGroup.addMenuItem(register);

        AuthContext ctx = new AuthContext();
        MenuNavigator menuRunner = new MenuNavigator(authMenuGroup, new CliMenuRenderer());

    }

    private static CommandFactory getActionFactory() {
        SessionStore sessionStore = new InMemorySessionStore();
        AccountStore accountStore = new InMemoryAccountStore();
        CredentialStore credRepo = new InMemoryCredentialStore();
        PasswordHasher hasher = new BCryptHasher();

        SessionManager sessionManager = new SessionManager(sessionStore);
        AccountService accountService = new AccountService(accountStore);
        AuthService authService = new AuthService(credRepo, hasher);
        CliIO io = new CliIO(System.in, System.out);

        CommandFactory actionFactory = new CommandFactory(
                io,
                io,
                authService,
                sessionManager,
                accountService
        );
        return actionFactory;
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