package com.fincore.app.controller;

import com.fincore.app.menu.CLIMenuComponent;
import com.fincore.app.menu.CLIMenuGroup;
import com.fincore.app.menu.MenuDirective;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MenuController {
    private final Deque<CLIMenuGroup> menuStack = new ArrayDeque<CLIMenuGroup>();;

    public MenuController(CLIMenuGroup root) {
        menuStack.push(root);
    }

    public void start() {
        Scanner sysIn = new Scanner(System.in);
        CLIMenuGroup currentMenu;
        int userInput;
        MenuDirective directive;
        int stackSize;

        for (;;) {
            currentMenu = menuStack.peek();
            stackSize = menuStack.size();
            if (Objects.isNull(currentMenu)) throw new NullPointerException("Menu does not exist");

            renderMenu(currentMenu);
            userInput = getInput(sysIn, currentMenu.getMenuSize());
            directive = interpretCommandOrExit(userInput, stackSize, currentMenu);
            switch (directive) {
                case EXIT -> System.exit(0);
                case BACK -> menuStack.pop();
                case GOTO_CHILD, STAY -> {}
            }

        }
    }

    public int getInput(Scanner inStream, int menuSize) {
        int userInput = -1;
        boolean isValid = false;
        
        while (!isValid) {
            if (!inStream.hasNextInt()) {
                System.out.print("Please enter a valid option: ");
                inStream.next();
                continue;
            }
            
            userInput = inStream.nextInt();
            if (userInput < 0 || userInput > menuSize) {
                System.out.print("Please enter a valid option: ");
                inStream.next();
                continue;
            }
            
            isValid = true;
        }
        return userInput;
    }

    public void renderMenu(CLIMenuGroup currentMenu) {
        // Get all options
        String breadcrumb = menuStack.stream().map(CLIMenuGroup::render).collect(Collectors.joining(" > "));
        String outputBody;

        System.out.println(breadcrumb);

        ArrayList<CLIMenuComponent> menuList = currentMenu.getChildren();

        outputBody = IntStream.range(0, currentMenu.getMenuSize())
                .mapToObj(i -> i + ". " + menuList.get(i).render())
                .collect(Collectors.joining("\n"));

        System.out.println(outputBody);
        // Compose the output string
    }

    public MenuDirective interpretCommandOrExit(int choice, int stackSize, CLIMenuGroup currentMenu) {
        if (choice == 0) {
            if (stackSize > 1) return MenuDirective.EXIT;
            else return MenuDirective.EXIT;
        }
        return callChild(choice, currentMenu);
    }

    public MenuDirective callChild(int index, CLIMenuGroup currentMenu) {
        // Add menu component on to the stack
        CLIMenuComponent child = currentMenu.getChild(index);
        if (child.isGroup()) menuStack.push((CLIMenuGroup) child);

        return child.select();
    }
}
