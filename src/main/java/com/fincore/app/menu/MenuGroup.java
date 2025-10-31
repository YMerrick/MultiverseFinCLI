package com.fincore.app.menu;

import com.fincore.app.presentation.cli.io.IOHandler;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuGroup implements MenuRenderer, MenuComponent, MenuIO {
    @Getter
    private final String label;
    private final IOHandler io;
    // Things the menu will be able to do
    //
    private final List<MenuItem> menuItems;

    public MenuGroup(String label, List<MenuItem> items, IOHandler io) {
        this.label = label;
        this.menuItems = items;
        this.io = io;
    }

    public MenuGroup(String label, IOHandler io) {
        this(label, new ArrayList<>(), io);
    }

    @Override
    public String render(List<String> listOfMenuStack) {
        io.renderBreadcrumb(listOfMenuStack);
        io.renderMenu(menuItems.stream().map(MenuItem::getLabel).toList());
        return "";
    }

    public void addMenuItem(MenuItem item) {
        if (Objects.isNull(item)) throw new IllegalArgumentException();
        menuItems.add(item);
    }

    @Override
    public int getMenuChoice() {
        int userInput;
        String line;
        String prompt;

        for (int i = 0;; i++) {
            prompt = i < 1 ?
                    "Please enter a menu index corresponding to your choice: " :
                    "Please enter a valid option: ";
            line = io.getInput(prompt);
            try {
                userInput = Integer.parseInt(line);
                if ((userInput >= 0) && (userInput < menuItems.size())) {
                    io.renderSpaces();
                    return userInput;
                }
            } catch (NumberFormatException ignored) {}
        }
    }

    public MenuItem getItem(int choice) {
        return menuItems.get(choice);
    }
}
