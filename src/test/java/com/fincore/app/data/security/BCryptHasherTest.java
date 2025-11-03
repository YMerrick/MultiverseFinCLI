package com.fincore.app.data.security;

import com.fincore.app.domain.identity.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;

@SuppressWarnings("SpellCheckingInspection")
public class BCryptHasherTest {
    private BCryptHasher testHasher;
    private final char[] stubPlainCharArray = {'h', 'e', 'l', 'l', 'o'};

    @BeforeEach
    void setUp() {
        byte[] fixedSalt = new byte[] {
                -104, -10, -53, -98,
                -92, -49, 67, -83,
                -46, 119, 118, -51,
                -22, -8, -4, -64
        };
        SecureRandom saltRandomiser = mock(SecureRandom.class);
        doAnswer(invocation -> {
            byte[] target = invocation.getArgument(0);
            System.arraycopy(fixedSalt, 0, target, 0, target.length);
            return null;
        }).when(saltRandomiser).nextBytes(any(byte[].class));
        this.testHasher = new BCryptHasher(saltRandomiser);
    }

    @Test
    void testHash() {
        String result = testHasher.hash(stubPlainCharArray);
        String expected = "$2y$12$kNZJloRNO41Qb1ZL4th6u.ZiLomEzLBHIcLX1UzHUHn.X6PlBdA0.";
        assertEquals(expected, result);
    }

    @Test
    void testVerify() {
        String testHash = testHasher.hash(stubPlainCharArray);
        assertTrue(testHasher.verify(stubPlainCharArray, testHash));
    }

    @Test
    void testVerifyExpectsFalse() {
        String testHash = "$2y$12$kNZJloRNO41Qb1ZL4th6u.ZiLomEzLBHIcLX1UzHUHn.X6PlBdA0,";
        assertFalse(testHasher.verify(stubPlainCharArray, testHash));
    }

    @Test
    void testNonDeterminism() {
        PasswordHasher testHasher = new BCryptHasher();
        String hash1 = testHasher.hash(stubPlainCharArray);
        String hash2 = testHasher.hash(stubPlainCharArray);
        assertNotEquals(hash1, hash2);
    }
}
