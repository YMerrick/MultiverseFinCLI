package com.fincore.app.cli.command;

import com.fincore.app.application.auth.Context;

public interface Command {
    public void execute(Context ctx);
}
