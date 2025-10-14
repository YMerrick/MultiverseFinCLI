package com.fincore.app.command;

import com.fincore.app.user.Account;

public class CommandFactory {
    private final Account account;
    public CommandFactory(Account account) {
        this.account = account;
    }

    public Command createDepositCommand(double amount) {
        return new DepositCommand(amount, account);
    }

    public Command createWithdrawCommand(double amount) {
        return new WithdrawCommand(amount, account);
    }
}
