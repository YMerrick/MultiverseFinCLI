package com.fincore.app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sysInScanObj = new Scanner(System.in);
        int menuChoice;
        double accountBalance = 10000.0;

        printWelcome(accountBalance, "Alex Doe");
        printBufferHeader("FinCore CLI Banking Menu");
        printMenu();
        System.out.print("Please enter the number corresponding your choice: ");
        menuChoice = getMenuChoice(sysInScanObj);

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