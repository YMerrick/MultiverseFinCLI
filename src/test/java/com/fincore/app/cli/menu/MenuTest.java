package com.fincore.app.cli.menu;

import static org.mockito.Mockito.*;

import com.fincore.app.cli.io.IOHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.util.ArrayList;

public class MenuTest {
    private static IOHandler stubIO;
    private Menu stubMenu;
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
        stubMenu = new Menu(stubLabel, stubList, stubIO);
    }

    @Test
    void testRender() {
        stubMenu.render(Mockito.anyList());
        verify(stubIO).renderBreadcrumb(Mockito.anyList());
        verify(stubIO).renderMenu(Mockito.anyList());
    }

    @Test
    void testAddMenuItemPassNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> stubMenu.addMenuItem(null)
        );
    }

    @Test
    void testGetMenuChoice() {
        when(stubIO.getInput()).thenReturn("0");
        int result = stubMenu.getMenuChoice();
        verify(stubIO).getInput();
        assertInstanceOf(Integer.class, result);
        assertEquals(0, result);
    }
}
