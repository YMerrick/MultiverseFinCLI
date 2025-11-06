package com.fincore.app.menu.actions;

import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.menu.model.MenuAction;
import com.fincore.app.menu.model.MenuDirective;
import com.fincore.app.menu.model.MenuGroup;
import com.fincore.app.menu.model.MenuResponse;
import com.fincore.app.presentation.cli.io.CliIO;
import com.fincore.app.presentation.cli.io.InputProvider;
import lombok.extern.java.Log;

import java.util.Optional;

// Made in a Factory
public class LoginAction implements MenuAction {
    private InputProvider input;
    private MenuGroup subMenu;
    @Override
    public MenuResponse run(AuthContext ctx) {
        String message = "Login Failed";
        String username = this.input.getStringInput("");


        // Return one type if successful another if not
        return new MenuResponse(
                MenuDirective.STAY,
                Optional.empty(),
                Optional.of(message)
        );
    }
}
