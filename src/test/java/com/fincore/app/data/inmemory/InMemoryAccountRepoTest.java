package com.fincore.app.data.inmemory;

import com.fincore.app.domain.account.Account;
import com.fincore.app.domain.account.AccountRepo;
import com.fincore.app.domain.shared.DuplicateEntityException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class InMemoryAccountRepoTest {
    private static AccountRepo testStore;
    private static Account stubAcc;
    private static UUID stubId;

    @BeforeAll
    static void setUp() {
        testStore = new InMemoryAccountRepo();
        stubAcc = mock(Account.class);
        stubId = UUID.randomUUID();
        when(stubAcc.getId()).thenReturn(stubId);
    }

    @Test
    void testSave() {
        testStore.save(stubAcc);
        verify(stubAcc).getId();
    }

    @Test
    void testSavePutExistingAccount() {
        assertThrows(
                DuplicateEntityException.class,
                () -> testStore.save(stubAcc)
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
    void testFindById() {
        Optional<Account> result = testStore.getById(stubId);
        assertEquals(stubAcc, result.orElseThrow(RuntimeException::new));
        assertInstanceOf(Optional.class, result);
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Account> result = testStore.getById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdNullInput() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testStore.getById(null)
        );
    }
}
