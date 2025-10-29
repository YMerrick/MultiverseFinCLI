package com.fincore.app;

import com.fincore.app.cli.command.CommandHandler;
import com.fincore.app.cli.app.MenuController;
import com.fincore.app.cli.io.NumberedIO;
import com.fincore.app.cli.menu.CLIMenuComponent;
import com.fincore.app.model.account.Account;
import com.fincore.app.cli.command.CommandFactory;
import com.fincore.app.cli.menu.CLIMenuGroup;
import com.fincore.app.cli.menu.CLIMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Account user = new Account(UUID.randomUUID(), "TestUser",10_000);
        CommandFactory factory = new CommandFactory();
        CommandHandler controller = new CommandHandler(factory);

        NumberedIO numberMenu = new NumberedIO(System.out, System.in);

        List<CLIMenuComponent> authMenuList = new ArrayList<>();

        List<CLIMenuComponent> mainMenuList = new ArrayList<>();
        mainMenuList.add(new CLIMenuItem("Deposit", controller::handleDeposit));
        mainMenuList.add(new CLIMenuItem("Withdraw", controller::handleWithdraw));

        CLIMenuGroup accountMenu = new CLIMenuGroup(
                "Account",
                numberMenu
        );
        accountMenu.addMenuItem(new CLIMenuItem("Check Balance", controller::handleGetBalance));

        mainMenuList.add(accountMenu);

        CLIMenuGroup mainMenu = new CLIMenuGroup(
                "Main Menu",
                mainMenuList,
                numberMenu
        );

        authMenuList.add(mainMenu);

        CLIMenuGroup authMenu = new CLIMenuGroup(
                "User",
                authMenuList,
                numberMenu
        );

        MenuController menuRunner = new MenuController(authMenu);
        menuRunner.start();

    }
}