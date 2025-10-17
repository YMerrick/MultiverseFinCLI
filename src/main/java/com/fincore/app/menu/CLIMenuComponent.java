package com.fincore.app.menu;

public interface CLIMenuComponent {
    public String render();
    public MenuDirective select();
}