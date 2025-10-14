package com.fincore.app.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CLIMenuGroupTest {

    @Test
    public void testSelect() {
        CLIMenuGroup testMenuGroup = new CLIMenuGroup("test");
        MenuDirective result = testMenuGroup.select();
        assertEquals(MenuDirective.GOTO_CHILD, result);
    }

    @Test
    public void testGetChildrenLabels() {
        CLIMenuGroup testMenuGroup = new CLIMenuGroup("test");
        List<String> result = testMenuGroup.getChildrenLabels();
        assertTrue(result.isEmpty());

        String testLabel = "testLabel";

        CLIMenuItem mockItem = mock(CLIMenuItem.class);
        ArrayList<CLIMenuComponent> mockList= new ArrayList<CLIMenuComponent>();
        mockList.add(mockItem);
        when(mockItem.render()).thenReturn(testLabel);
        testMenuGroup = new CLIMenuGroup("test", mockList);

        result = testMenuGroup.getChildrenLabels();

        for (String resultLabel : result) {
            assertEquals(testLabel, resultLabel);
        }
    }

    @Test
    public void testGetMenuSize() {
        ArrayList<CLIMenuComponent> mockList = new ArrayList<CLIMenuComponent>();
        mockList.add(mock(CLIMenuItem.class));
        CLIMenuGroup testMenuGroup = new CLIMenuGroup("test", mockList);

        assertEquals(1, testMenuGroup.getMenuSize());
    }



}
