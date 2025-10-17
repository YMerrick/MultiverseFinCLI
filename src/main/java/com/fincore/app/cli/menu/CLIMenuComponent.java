package com.fincore.app.cli.menu;

public interface CLIMenuComponent {
    public String render();
    public MenuDirective select();
    public boolean isGroup();
}