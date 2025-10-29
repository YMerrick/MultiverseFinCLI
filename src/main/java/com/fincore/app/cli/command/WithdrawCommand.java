package com.fincore.app.cli.command;

import com.fincore.app.application.accounts.AccountService;

import java.util.UUID;

public record WithdrawCommand(double amount, AccountService service, UUID accId) implements Command {
    @Override
    public void execute() {
        service.withdraw(accId, amount);
    }
}
