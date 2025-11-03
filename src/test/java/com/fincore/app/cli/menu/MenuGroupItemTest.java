package com.fincore.app.cli.menu;

import com.fincore.app.application.auth.Context;
import com.fincore.app.menu.model.MenuItem;
import com.fincore.app.menu.model.MenuResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class MenuGroupItemTest {
    private MenuItem stubMenuItem;
    private final String stubLabel = "Test";
    private Context stubCtx;

    @BeforeEach
    void setUp() {
        stubCtx = mock(Context.class);
        stubMenuItem = MenuItem.builder()
                .label(stubLabel)
                .build();
    }

    @Test
    void testRun() {
        MenuResponse result = stubMenuItem.run(stubCtx);
        assertInstanceOf(MenuResponse.class, result);
    }
}
