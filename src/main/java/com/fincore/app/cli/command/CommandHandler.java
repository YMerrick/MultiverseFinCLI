package com.fincore.app.cli.command;

public class CommandHandler {
    private final CommandFactory factory;

    public CommandHandler(CommandFactory factory) {
        this.factory = factory;
    }

    public void handleDeposit() {
        double amount = 15; // User input here <<---

        Command depositCommand = factory.createDepositCommand(amount);
        depositCommand.execute();
    }

    public void handleWithdraw() {
        double amount = 15;
        Command withdrawCommand = factory.createWithdrawCommand(amount);
        withdrawCommand.execute();
    }
}
