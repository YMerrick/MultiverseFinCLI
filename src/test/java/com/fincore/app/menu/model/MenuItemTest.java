package com.fincore.app.menu.model;

import com.fincore.app.application.auth.AuthContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuItemTest {
    private MenuItem stubMenuItem;
    @Mock
    private AuthContext stubCtx;
    @Mock
    private MenuAction stubAction;
    @Mock
    private MenuResponse stubRes;


    @BeforeEach
    void setUp() {
        String stubLabel = "Test";
        stubMenuItem = new MenuItem(stubLabel, stubAction);
    }

    @Test
    void testRun() {
        doReturn(stubRes).when(stubAction).run(stubCtx);

        MenuResponse result = stubMenuItem.run(stubCtx);

        verify(stubAction).run(stubCtx);
        assertInstanceOf(MenuResponse.class, result);
    }
}
