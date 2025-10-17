package com.fincore.app.cli.command;

import com.fincore.app.model.account.Account;

public record DepositCommand(double amount, Account account) implements Command {
    @Override
    public void execute() {
        account.deposit(amount);
    }
}
