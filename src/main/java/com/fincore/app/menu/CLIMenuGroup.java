package com.fincore.app.menu;

import java.util.ArrayList;

public class CLIMenuGroup implements CLIMenuComponent {
    private final String label;
    private final ArrayList<CLIMenuComponent> menuItemList;

    public CLIMenuGroup(String label, ArrayList<CLIMenuComponent> menuItems) {
        this.label = label;
        this.menuItemList = menuItems;
    }

    public CLIMenuGroup(String label) {
        this.label = label;
        this.menuItemList = new ArrayList<CLIMenuComponent>();
    }

    // Runs the submenu
    @Override
    public void select() {
        for (CLIMenuComponent item : menuItemList) {
            item.display();
        }
    }

    @Override
    public void display() {
        System.out.println(label);
    }

    public void addMenuItem(CLIMenuComponent item) {
        menuItemList.add(item);
    }

    public void removeMenuItem(CLIMenuComponent item) {
        menuItemList.remove(item);
    }

    public void removeMenuItem(int index) {
        menuItemList.remove(index);
    }
}
