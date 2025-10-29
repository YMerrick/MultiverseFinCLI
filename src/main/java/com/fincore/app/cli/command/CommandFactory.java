package com.fincore.app.cli.command;

import com.fincore.app.application.accounts.AccountService;
import com.fincore.app.application.auth.SessionManager;
import com.fincore.app.cli.io.IOHandler;
import com.fincore.app.model.identity.Session;

import java.util.Optional;
import java.util.UUID;

public class CommandFactory {
    private SessionManager sessionManager;
    private AccountService service;
    private IOHandler io;
    public CommandFactory() {
    }

    public Command createDepositCommand(double amount, UUID sessionId) {
        UUID accId = getAccId(sessionId);
        return new DepositCommand(amount, service, accId);
    }

    public Command createWithdrawCommand(double amount, UUID sessionId) {
        UUID accId = getAccId(sessionId);
        return new WithdrawCommand(amount, service, accId);
    }

    public Command createGetBalance(UUID sessionId) {
        UUID accId = getAccId(sessionId);
        return new GetBalanceCommand(io, service, accId);
    }

    private UUID getAccId(UUID sessionId) {
        Optional<Session> session = sessionManager.validate(sessionId);
        if (session.isEmpty()) throw new RuntimeException();
        return session.get().accId();
    }
}
