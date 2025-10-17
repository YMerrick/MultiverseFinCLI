package com.fincore.app.menu;

import java.util.ArrayList;

public class CLIMenuGroup implements CLIMenuComponent {
    private final String label;
    private final ArrayList<CLIMenuComponent> menuItemList;
    private final boolean enabled;

    public CLIMenuGroup(String label, ArrayList<CLIMenuComponent> menuItems, boolean isEnabled) {
        this.label = label;
        this.menuItemList = new ArrayList<>(menuItems);
        this.enabled = isEnabled;
    }

    public CLIMenuGroup(String label, boolean isEnabled) {
        this.label = label;
        this.enabled = isEnabled;
        this.menuItemList = new ArrayList<CLIMenuComponent>();
    }

    public CLIMenuGroup(String label, ArrayList<CLIMenuComponent> menuItems) {
        this.label = label;
        this.enabled = true;
        this.menuItemList = new ArrayList<>(menuItems);
    }

    public CLIMenuGroup(String label) {
        this.label = label;
        this.menuItemList = new ArrayList<CLIMenuComponent>();
        this.enabled = true;
    }

    // Runs the submenu
    @Override
    public MenuDirective select() {
        return MenuDirective.GOTO_CHILD;
    }

    @Override
    public String render() {
        return label;
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    public ArrayList<CLIMenuComponent> getChildren() {
        return menuItemList;
    }

    public CLIMenuComponent getChild(int index) {
        return menuItemList.get(index);
    }

    public void addMenuItem(CLIMenuComponent item) {
        menuItemList.add(item);
    }

    public int getMenuSize() {
        return menuItemList.size();
    }

    public void removeMenuItem(CLIMenuComponent item) {
        menuItemList.remove(item);
    }

    public void removeMenuItem(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= menuItemList.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        menuItemList.remove(index);
    }
}
