package com.fincore.app.application.auth;

import com.fincore.app.domain.identity.CredentialRepo;
import com.fincore.app.domain.identity.Credentials;
import com.fincore.app.domain.identity.PasswordHasher;
import com.fincore.app.domain.shared.AuthException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    @Test
    public void testLogin() {
        UUID stubAccId = UUID.randomUUID();
        String stubUsername = "TestUser";
        char[] emptyCharArray = {};
        CredentialRepo stubCredStore = mock(CredentialRepo.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        when(stubCredStore.getByUsername(stubUsername))
                .thenReturn(
                        Optional.of(new Credentials(stubUsername, "random", stubAccId))
                );
        when(stubHasher.verify(emptyCharArray, "random")).thenReturn(true);

        UUID result = null;
        try {
            result = new AuthService(stubCredStore, stubHasher).login(stubUsername, emptyCharArray);
        } catch (AuthException ignored) {}

        assertEquals(stubAccId, result);
    }

    @Test
    public void testNoUserLogin() {
        CredentialRepo stubCredStore = mock(CredentialRepo.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        when(stubCredStore.getByUsername(Mockito.any())).thenReturn(Optional.empty());

        AuthService authTest = new AuthService(stubCredStore, stubHasher);
        assertThrows(
                AuthException.class,
                () -> authTest.login("", new char[] {})
        );
    }

    @Test
    public void testWrongPasswordLogin() {
        CredentialRepo stubCredStore = mock(CredentialRepo.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        Credentials stubCredential = mock(Credentials.class);
        when(stubHasher.verify(Mockito.any(), Mockito.any())).thenReturn(false);
        when(stubCredStore.getByUsername(Mockito.any())).thenReturn(Optional.of(stubCredential));

        AuthService authTest = new AuthService(stubCredStore, stubHasher);
        assertThrows(
                AuthException.class,
                () -> authTest.login("", new char[] {})
        );
    }

    @Test
    public void testRegister() {
        // Test whether AuthService calls save?
        CredentialRepo stubCredStore = mock(CredentialRepo.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        UUID stubId = UUID.randomUUID();

        AuthService testAuth = new AuthService(stubCredStore, stubHasher);
        testAuth.register("", new char[] {}, stubId);

        verify(stubCredStore, times(1)).save(Mockito.any());
        verify(stubHasher, times(1)).hash(Mockito.any());
    }

    @Test
    public void testRegisterExistingUser() {
        CredentialRepo stubCredStore = mock(CredentialRepo.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        Credentials stubCreds = mock(Credentials.class);
        Optional<Credentials> stubOptional = Optional.ofNullable(stubCreds);

        when(stubCredStore.getByUsername(Mockito.any())).thenReturn(stubOptional);

        AuthService testAuth = new AuthService(stubCredStore, stubHasher);

        assertThrows(
                AuthException.class,
                () -> testAuth.register("Test", new char[] {}, UUID.randomUUID())
        );
    }
}
