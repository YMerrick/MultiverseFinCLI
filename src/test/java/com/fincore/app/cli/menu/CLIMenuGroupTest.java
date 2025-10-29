package com.fincore.app.cli.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class CLIMenuGroupTest {

    @Test
    public void testSelect() {
        CLIMenuGroup testMenuGroup = new CLIMenuGroup("test");
        MenuDirective result = testMenuGroup.select();
        assertEquals(MenuDirective.GOTO_MENU, result);
    }

    @Test
    public void testGetMenuSize() {
        ArrayList<CLIMenuComponent> mockList = new ArrayList<CLIMenuComponent>();
        mockList.add(mock(CLIMenuItem.class));
        CLIMenuGroup testMenuGroup = new CLIMenuGroup("test", mockList);

        assertEquals(1, testMenuGroup.getMenuSize());
    }



}
