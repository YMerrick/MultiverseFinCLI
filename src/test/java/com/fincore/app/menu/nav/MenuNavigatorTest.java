package com.fincore.app.menu.nav;

import com.fincore.app.application.auth.AuthContext;
import com.fincore.app.menu.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayDeque;
import java.util.Optional;

import static com.fincore.app.menu.model.MenuDirective.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MenuNavigatorTest {
    @Spy
    private ArrayDeque<MenuGroup> spyStack;
    private MenuNavigator testNav;
    @Mock
    private MenuResponse stubResponse;
    @Mock
    private MenuGroup stubMenuGroup;
    @Mock
    private MenuRenderer stubRenderer;

    @BeforeEach
    void setUp() {
        testNav = new MenuNavigator(stubMenuGroup, stubRenderer, spyStack);
    }

    @Test
    @DisplayName("interpretDirective test - with exit directive")
    void testInterpretDirectiveWithExit() {
        when(stubResponse.directive()).thenReturn(EXIT);
        testNav.interpretDirective(stubResponse);
        assertTrue(testNav.isExit());
    }

    @Test
    @DisplayName("interpretDirect test - with GOTO_MENU directive")
    void testInterpretDirectiveWithGOTO() {
        when(stubResponse.directive()).thenReturn(GOTO_MENU);
        when(stubResponse.submenu()).thenReturn(Optional.of(stubMenuGroup));
        testNav.interpretDirective(stubResponse);
        assertEquals(2, spyStack.size());
    }

    @Test
    @DisplayName("interpretDirective test - with Back directive")
    void testInterpretDirectiveWithBack() {
        when(stubResponse.directive()).thenReturn(GOTO_MENU);
        when(stubResponse.submenu()).thenReturn(Optional.of(stubMenuGroup));
        testNav.interpretDirective(stubResponse);
        when(stubResponse.directive()).thenReturn(BACK);
        testNav.interpretDirective(stubResponse);
        assertEquals(1, spyStack.size());
    }

    @Test
    @DisplayName("interpretDirective test - with null directive")
    void testInterpretDirectiveWithNull() {
        when(stubResponse.directive()).thenReturn(null);
        assertThrows(
                IllegalStateException.class,
                () -> testNav.interpretDirective(stubResponse)
        );
    }

    @Test
    @DisplayName("interpretDirective test - with null submenu")
    void testInterpretDirectiveWithNullSubmenu() {
        when(stubResponse.directive()).thenReturn(GOTO_MENU);
        when(stubResponse.submenu()).thenReturn(Optional.empty());
        assertThrows(
                IllegalStateException.class,
                () -> testNav.interpretDirective(stubResponse)
        );
    }

    @Test
    @DisplayName("select test - with happy path")
    void testSelect() {
        int stubChoice = 0;
        MenuItem mockItem = mock(MenuItem.class);
        AuthContext stubCtx = mock(AuthContext.class);

        doReturn(mockItem).when(stubMenuGroup).getItem(stubChoice);
        when(mockItem.run(stubCtx)).thenReturn(stubResponse);
        testNav.select(stubChoice, stubCtx);

        verify(mockItem).run(stubCtx);
    }

    @Test
    @DisplayName("render test - with happy path")
    void testRender() {
        testNav.render();
        verify(stubRenderer).render(anyList(), anyList());
    }
}
