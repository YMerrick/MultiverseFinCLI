package com.fincore.app.cli.command;

import com.fincore.app.application.accounts.AccountService;

import java.util.UUID;

public record DepositCommand(double amount, AccountService serviceHandler, UUID accId) implements Command {
    @Override
    public void execute() {
        serviceHandler.deposit(accId, amount);
    }
}
