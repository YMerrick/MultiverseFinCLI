package com.fincore.app.cli.menu;

import com.fincore.app.cli.io.IOHandler;
import com.fincore.app.cli.io.NumberedIO;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CLIMenuGroup implements CLIMenuComponent {
    private final String label;
    private final List<CLIMenuComponent> menuItemList;
    private final IOHandler ioHandler;

    public CLIMenuGroup(String label, List<CLIMenuComponent> menuItems, IOHandler ioHandler) {
        this.label = label;
        this.menuItemList = new ArrayList<>(menuItems);
        this.ioHandler = ioHandler;
    }

    public CLIMenuGroup(String label, IOHandler ioHandler) {
        this(label, new ArrayList<>(), ioHandler);
    }

    public CLIMenuGroup(String label, List<CLIMenuComponent> menuItems) {
        this(label, menuItems, new NumberedIO(System.out, System.in));
    }

    public CLIMenuGroup(String label) {
        this(label, new ArrayList<>(), new NumberedIO(System.out, System.in));
    }

    // Runs the submenu
    @Override
    public MenuDirective select() {
        return MenuDirective.GOTO_CHILD;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    // Returns the choice of the user
    public int getMenuChoice() {
        // Validate input
        int userInput;
        String line;

        while (true) {
            line = ioHandler.getInput();
            try {
                userInput = Integer.parseInt(line);
                if ((userInput >= 0) && (userInput <= menuItemList.size())) return userInput;
            } catch (NumberFormatException ignored) {}
            System.out.print("Please enter a valid option: ");
        }
    }

    // Renders the menu
    public void render(List<String> menuStack) {
        ioHandler.renderMenu(menuStack, getChildrenLabels());
    }

    private List<String> getChildrenLabels() {
        return menuItemList.stream().map(CLIMenuComponent::getLabel).collect(Collectors.toList());
    }

    public CLIMenuComponent getChild(int index) {
        if (menuItemList.isEmpty()) throw new ArrayIndexOutOfBoundsException("The menu is empty");
        return menuItemList.get(index);
    }

    public void addMenuItem(CLIMenuComponent item) {
        menuItemList.add(item);
    }

    public int getMenuSize() {
        return menuItemList.size();
    }
}
