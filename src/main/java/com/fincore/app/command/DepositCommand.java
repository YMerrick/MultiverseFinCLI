package com.fincore.app.command;

import com.fincore.app.user.Account;

public record DepositCommand(double amount, Account account) implements Command {
    @Override
    public void execute() {
        account.deposit(amount);
    }
}
