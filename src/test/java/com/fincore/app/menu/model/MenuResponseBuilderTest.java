package com.fincore.app.menu.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fincore.app.menu.model.MenuDirective.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MenuResponseBuilderTest {
    @Test
    @DisplayName("MenuResponseBuilder test - default build")
    void testBuilderDefault() {
        MenuResponse result = new MenuResponseBuilder().build();
        assertInstanceOf(MenuResponse.class, result);
        assertInstanceOf(Optional.class, result.submenu());
        assertInstanceOf(Optional.class, result.message());
        assertTrue(result.message().isEmpty());
        assertTrue(result.submenu().isEmpty());
        assertEquals(STAY, result.directive());
    }

    @Test
    @DisplayName("MenuResponseBuilder test - directive is null")
    void testBuilderDirectiveIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new MenuResponseBuilder()
                        .directive(null)
                        .build()
        );
    }

    @Test
    @DisplayName("MenuResponseBuilder test - with message")
    void testBuilderWithMessage() {
        MenuResponse result = new MenuResponseBuilder()
                .message("Test")
                .build();

        assertTrue(result.message().isPresent());
        assertEquals("Test", result.message().get());
    }

    @Test
    @DisplayName("MenuResponseBuilder test - with submenu")
    void testBuilderWithMenu() {
        MenuResponse result = new MenuResponseBuilder()
                .submenu(new MenuGroup("Test"))
                .build();

        assertTrue(result.submenu().isPresent());
        assertInstanceOf(MenuGroup.class, result.submenu().get());
    }

    @ParameterizedTest
    @EnumSource(MenuDirective.class)
    @DisplayName("MenuResponseBuilder test - directives")
    void testBuilderWithDirective(MenuDirective directive) {
        MenuResponse result = new MenuResponseBuilder()
                .directive(directive)
                .build();
        assertEquals(directive, result.directive());
    }
}
