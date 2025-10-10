package com.fincore.app;

import com.fincore.app.controller.CommandHandler;
import com.fincore.app.controller.MenuController;
import com.fincore.app.model.Account;
import com.fincore.app.factory.CommandFactory;
import com.fincore.app.menu.CLIMenuGroup;
import com.fincore.app.menu.CLIMenuItem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Account user = new Account(10_000);
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