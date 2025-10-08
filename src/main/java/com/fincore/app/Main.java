package com.fincore.app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
            };
        }
    }

    public static double deposit(double balance, Scanner input) {
        System.out.print("Enter amount to deposit: ");
        return balance + getDoubleInput(input);
    }

    public static double withdraw(double balance, Scanner input) {
        System.out.print("Enter amount to withdraw: ");
        return balance - getDoubleInput(input);
    }

    public static void printBalance(double balance) {
        String balanceMsg = "Current balance: $%,.2f";
        System.out.printf((balanceMsg) + "%n", balance);
    }

    public static double getDoubleInput(Scanner input) {
        while (!input.hasNextDouble()) {
            input.next();
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