package com.fincore.app.cli.menu;

import com.fincore.app.cli.io.IOHandler;
import com.fincore.app.model.menu.MenuChoiceProvider;
import com.fincore.app.model.menu.MenuDisplayable;
import com.fincore.app.model.menu.MenuComponent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Menu implements MenuDisplayable, MenuComponent, MenuChoiceProvider {
    @Getter
    private final String label;
    private final IOHandler io;
    // Things the menu will be able to do
    //
    private final List<MenuItem> menuItems;

    public Menu(String label, List<MenuItem> items, IOHandler io) {
        this.label = label;
        this.menuItems = items;
        this.io = io;
    }

    public Menu(String label, IOHandler io) {
        this(label, new ArrayList<>(), io);
    }

    public void render(List<String> listOfMenuStack) {
        io.renderBreadcrumb(listOfMenuStack);
        io.renderMenu(menuItems.stream().map(MenuItem::getLabel).toList());
    }

    public void addMenuItem(MenuItem item) {
        if (Objects.isNull(item)) throw new IllegalArgumentException();
        menuItems.add(item);
    }

    @Override
    public int getMenuChoice() {
        int userInput;
        String line;

        while (true) {
            line = io.getInput();
            try {
                userInput = Integer.parseInt(line);
                if ((userInput >= 0) && (userInput < menuItems.size())) return userInput;
            } catch (NumberFormatException ignored) {}
            System.out.print("Please enter a valid option: ");
        }
    }

    public MenuItem getItem(int choice) {
        return menuItems.get(choice);
    }
}
