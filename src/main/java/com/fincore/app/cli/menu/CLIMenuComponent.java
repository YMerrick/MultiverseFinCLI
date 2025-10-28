package com.fincore.app.cli.menu;

public interface CLIMenuComponent {
    public String getLabel();
    public MenuDirective select();
    public boolean isGroup();
}