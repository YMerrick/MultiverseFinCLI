package com.fincore.app.cli.menu;

import com.fincore.app.cli.menu.CLIMenuItem;
import com.fincore.app.cli.menu.MenuDirective;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CLIMenuItemTest {
    String stubLabel = "test";
    boolean actionFlag = false;
    CLIMenuItem menuItem = new CLIMenuItem(stubLabel, this::stubAction);

    @Test
    public void testSelect() {
        MenuDirective result = menuItem.select();
        assertEquals(MenuDirective.STAY, result);
        assertTrue(actionFlag);
    }

    private void stubAction() {
        actionFlag = true;
    }
}
