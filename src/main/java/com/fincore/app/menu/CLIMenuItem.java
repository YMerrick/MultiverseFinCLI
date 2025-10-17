package com.fincore.app.menu;

public class CLIMenuItem implements CLIMenuComponent {
    private final String label;
    private final Runnable action;

    public CLIMenuItem(String label, Runnable action) {
        this.label = label;
        this.action = action;
    }

    @Override
    public String display() {
        return label;
    }

    @Override
    public MenuDirective select() {
        action.run();
        return MenuDirective.STAY;
    }
}
