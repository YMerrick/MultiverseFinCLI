package com.fincore.app.menu.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuGroup implements MenuComponent {
    @Getter
    private final String label;
//    private final IOHandler io;
    // Things the menu will be able to do
    //
    private final List<MenuItem> menuItems;

    public MenuGroup(String label, List<MenuItem> items) {
        this.label = label;
        this.menuItems = items;
//        this.io = io;
    }

    public MenuGroup(String label) {
        this(label, new ArrayList<>());
    }

    public List<String> getItemLabels() {
        return menuItems.stream().map(MenuItem::getLabel).toList();
    }

    public void addMenuItem(MenuItem item) {
        if (Objects.isNull(item)) throw new IllegalArgumentException();
        menuItems.add(item);
    }

    public MenuItem getItem(int choice) throws IndexOutOfBoundsException{
        return menuItems.get(choice);
    }
}
