package com.fincore.app.command;

import com.fincore.app.model.Account;

public record DepositCommand(double amount, Account account) implements Command {
    @Override
    public void execute() {
        account.deposit(amount);
    }
}
