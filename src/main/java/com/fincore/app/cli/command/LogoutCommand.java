package com.fincore.app.cli.command;

import com.fincore.app.application.auth.Context;
import com.fincore.app.application.auth.SessionManager;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class LogoutCommand implements Command{
    private SessionManager service;


    @Override
    public void execute(Context ctx) {
        service.terminate(ctx.getSession());
    }
}
