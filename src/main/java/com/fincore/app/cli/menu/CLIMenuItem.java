package com.fincore.app.cli.menu;

public class CLIMenuItem implements CLIMenuComponent {
    private final String label;
    private final Runnable action;

    public CLIMenuItem(String label, Runnable action) {
        this.label = label;
        this.action = action;
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    @Override
    public String render() {
        return label;
    }

    @Override
    public MenuDirective select() {
        action.run();
        return MenuDirective.STAY;
    }
}
