package com.fincore.app.cli.menu;

import static org.mockito.Mockito.*;

import com.fincore.app.presentation.cli.io.IOHandler;
import com.fincore.app.menu.model.MenuGroup;
import com.fincore.app.menu.model.MenuItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.util.ArrayList;

public class MenuGroupTest {
    private static IOHandler stubIO;
    private MenuGroup stubMenuGroup;
    private final String stubLabel = "Test";
    private MenuItem stubItem;
    private ArrayList stubList;

    @BeforeAll
    static void setUpClass() {
        stubIO = mock(IOHandler.class);
    }

    @BeforeEach
    void setUp() {
        stubList = new ArrayList<MenuItem>();
        stubItem = mock(MenuItem.class);
        stubList.add(stubItem);
        stubMenuGroup = new MenuGroup(stubLabel, stubList, stubIO);
    }

    @Test
    void testRender() {
        stubMenuGroup.render(Mockito.anyList());
        verify(stubIO).renderBreadcrumb(Mockito.anyList());
        verify(stubIO).renderMenu(Mockito.anyList());
    }

    @Test
    void testAddMenuItemPassNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> stubMenuGroup.addMenuItem(null)
        );
    }

    @Test
    void testGetMenuChoice() {
        when(stubIO.getInput(anyString())).thenReturn("0");
        int result = stubMenuGroup.getMenuChoice();
        verify(stubIO).getInput(anyString());
        assertInstanceOf(Integer.class, result);
        assertEquals(0, result);
    }
}
