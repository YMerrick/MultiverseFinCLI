package com.fincore.app.application.auth;

import com.fincore.app.domain.identity.SessionStore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SessionManagerTest {
    @Test
    void testIssue() {
        SessionStore stubStore = mock(SessionStore.class);
        SessionManager stubManager = new SessionManager(stubStore);

        assertInstanceOf(UUID.class, stubManager.issue(UUID.randomUUID()));
        verify(stubStore, times(1)).save(Mockito.any(), Mockito.any());
    }

    @Test
    void testIssueNullArgument() {
        SessionStore stubStore = mock(SessionStore.class);
        SessionManager stubManager = new SessionManager(stubStore);
        assertThrows(
                IllegalArgumentException.class,
                () -> stubManager.issue(null)
        );
    }
}
