package com.fincore.app;

import com.fincore.app.cli.command.CommandHandler;
import com.fincore.app.cli.app.MenuController;
import com.fincore.app.model.account.Account;
import com.fincore.app.cli.command.CommandFactory;
import com.fincore.app.cli.menu.CLIMenuGroup;
import com.fincore.app.cli.menu.CLIMenuItem;

public class Main {
    public static void main(String[] args) {

        Account user = new Account("TestUser",10_000);
        CommandFactory factory = new CommandFactory(user);
        CommandHandler controller = new CommandHandler(factory);

        CLIMenuGroup mainMenu = new CLIMenuGroup("Main Menu");
        mainMenu.addMenuItem(new CLIMenuItem("Deposit", controller::handleDeposit));
        mainMenu.addMenuItem(new CLIMenuItem("Withdraw", controller::handleWithdraw));

        CLIMenuGroup accountMenu = new CLIMenuGroup("Accounts");
//        accountMenu.addMenuItem(new CLIMenuItem("Check Balance", ));

        mainMenu.addMenuItem(accountMenu);

        MenuController menuRunner = new MenuController(mainMenu);
        menuRunner.start();

    }
}