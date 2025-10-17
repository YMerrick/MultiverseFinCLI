package com.fincore.app.factory;

import com.fincore.app.command.WithdrawCommand;
import com.fincore.app.model.Account;
import com.fincore.app.command.Command;
import com.fincore.app.command.DepositCommand;

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
