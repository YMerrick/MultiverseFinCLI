package com.fincore.app.cli.command;

import java.util.UUID;

public class CommandHandler {
    private final CommandFactory factory;
    // Find a better way to retrieve session ID
    private UUID sessionId;

    public CommandHandler(CommandFactory factory) {
        this.factory = factory;
    }

    public void handleDeposit() {
        // Get user input
        double amount = 0.0;

        Command depositCommand = factory.createDepositCommand(amount, sessionId);
        depositCommand.execute();
        // Log command here
    }

    public void handleWithdraw() {
        double amount = 15;
        Command withdrawCommand = factory.createWithdrawCommand(amount, sessionId);
        withdrawCommand.execute();
        // Log command here
    }

    public void handleGetBalance() {
        Command getBalance = factory.createGetBalance(sessionId);
        getBalance.execute();
        // Log command here
    }
}
