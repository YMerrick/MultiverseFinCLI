package com.fincore.app.cli.command;

import com.fincore.app.model.account.Account;

public record WithdrawCommand( double amount, Account account) implements Command {
    @Override
    public void execute() {
        account.withdraw(amount);
    }
}
