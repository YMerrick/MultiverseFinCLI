package com.fincore.app.data.security;

import com.fincore.app.domain.identity.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NoHasherTest {
    private PasswordHasher hasher;

    @BeforeEach
    void setUp() {
        hasher = new NoHasher();
    }

    @Test
    void testHash() {
        char[] testPassword = {'h', 'e', 'l', 'l', 'o', '\n'};
        String result = hasher.hash(testPassword);
        assertEquals("hello", result);
    }

    @Test
    void testVerify() {
        char[] testPassword = {'h', 'e', 'l', 'l', 'o', '\n'};
        assertTrue(hasher.verify(testPassword, "hello"));
    }
}
