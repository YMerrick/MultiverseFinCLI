package com.fincore.app.data.inmemory;

import com.fincore.app.domain.identity.CredentialRepo;
import com.fincore.app.domain.identity.Credentials;
import com.fincore.app.domain.shared.DuplicateEntityException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InMemoryCredentialRepoTest {
    private static CredentialRepo testStore;
    private static Credentials stubCred;

    @BeforeAll
    static void setUp() {
        testStore = new InMemoryCredentialRepo();
        stubCred = mock(Credentials.class);
    }

    @Test
    void testSave() {
        when(stubCred.username()).thenReturn("Test");
        testStore.save(stubCred);
        verify(stubCred, times(1)).username();
    }

    @Test
    void testSavePutExistingUser() {
        assertThrows(
                DuplicateEntityException.class,
                () -> testStore.save(stubCred)
        );
    }

    @Test
    void testSavePutNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testStore.save(null)
        );
    }

    @Test
    void testFindByUsername() {
        Optional<Credentials> result = testStore.getByUsername("Test");
        assertEquals(stubCred, result.orElseThrow(RuntimeException::new));
        assertInstanceOf(Optional.class, result);
    }

    @Test
    void testFindByUsernameNotFound() {
        Optional<Credentials> result = testStore.getByUsername("Nothing");
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByUsernameEmptyString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testStore.getByUsername("")
        );
    }
}
