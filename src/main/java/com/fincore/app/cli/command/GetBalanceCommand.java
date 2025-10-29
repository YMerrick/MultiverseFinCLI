package com.fincore.app.cli.command;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.cli.io.IOHandler;
import com.fincore.app.model.shared.Money;
import com.fincore.app.model.shared.MoneyFormatter;

import java.util.UUID;

public record GetBalanceCommand(IOHandler io, AccountService service, UUID accId) implements Command {
    @Override
    public void execute() {
        Money balance = service.getBalance(accId);
        String formattedBalance = MoneyFormatter.format(balance);
        io.renderOutput(formattedBalance);
    }
}
