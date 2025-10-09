package com.fincore.app.command;

import com.fincore.app.model.Account;

public record WithdrawCommand( double amount, Account account) implements Command {
    @Override
    public void execute() {
        account.withdraw(amount);
    }
}
