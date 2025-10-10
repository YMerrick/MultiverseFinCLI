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


        Scanner sysInScanObj = new Scanner(System.in);
        int menuChoice;
        double accountBalance = 10000.0;

        printWelcome(accountBalance, "Alex Doe");
        printBufferHeader("FinCore CLI Banking Menu");
        while (true) {
            printMenu();
            System.out.print("Please enter the number corresponding your choice: ");
            menuChoice = getMenuChoice(sysInScanObj);
            switch (menuChoice) {
                case 1 -> accountBalance = deposit(accountBalance, sysInScanObj);
                case 2 -> accountBalance = withdraw(accountBalance, sysInScanObj);
                case 3 -> printBalance(accountBalance);
                case 0 -> System.exit(0);
            }

        }
    }

    public static double deposit(double balance, Scanner input) {
        double userInput;
        do {
            System.out.print("Enter amount to deposit: ");
            userInput = getDoubleInput(input);
        } while (userInput < 0);
        return balance + userInput;
    }

    public static double withdraw(double balance, Scanner input) {
        double userInput;
        do {
            System.out.print("Enter amount to withdraw: ");
            userInput = getDoubleInput(input);
        } while (userInput < 0);
        return balance - userInput;
    }

    public static void printBalance(double balance) {
        String balanceMsg = "Current balance: $%,.2f";
        System.out.printf((balanceMsg) + "%n", balance);
    }

    public static double getDoubleInput(Scanner input) {
        while (!input.hasNextDouble()) {
            input.next();
            System.out.print("Please enter a valid amount: ");
        }
        return input.nextDouble();
    }

    public static int getMenuChoice(Scanner input) {
        while (!input.hasNextInt()) {
            input.next();
        }
        return input.nextInt();
    }

    public static void printWelcome(double balance, String accName) {
        String welcomeMsg = """
                Welcome to FinCore CLI Banking!
                Account Holder: %2$s
                Initial Balance: $%1$,.2f
                """;
        welcomeMsg = String.format(welcomeMsg, balance, accName);
        System.out.println(welcomeMsg);
    }

    public static void printBufferHeader(String msg) {
        String output = " === " + msg + " === ";
        System.out.println(output);
    }

    public static void printMenu() {
        String menuMsg = """
                1. Deposit
                2. Withdraw
                3. Check Balance
                0. Exit""";
        System.out.println(menuMsg);
    }
}