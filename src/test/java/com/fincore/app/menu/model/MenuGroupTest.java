package com.fincore.app.menu.model;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MenuGroupTest {
    private MenuGroup testGroup;
    private MenuItem stubItem;
    @Spy
    private ArrayList<MenuItem> spyList;

    @BeforeEach
    void setUp() {
        stubItem = mock(MenuItem.class);
        spyList.add(stubItem);
        String stubLabel = "Test";
        testGroup = new MenuGroup(stubLabel, spyList);
    }

    @Test
    void testGetItemLabels() {
        doReturn("Random Label").when(stubItem).getLabel();
        List<String> result = testGroup.getItemLabels();
        assertInstanceOf(List.class, result);
        assertLinesMatch(List.of(new String[]{"Random Label"}), result);
    }

    @Test
    void testAddMenuItemWithNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testGroup.addMenuItem(null)
        );
    }

    @Test
    @DisplayName("addMenuItem test - happy path")
    void testAddMenuItem() {
        testGroup.addMenuItem(stubItem);
        assertEquals(2, spyList.size());
    }
}
