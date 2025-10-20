package com.fincore.app.application.auth;

import com.fincore.app.model.account.AccountId;
import com.fincore.app.model.identity.CredentialStore;
import com.fincore.app.model.identity.Credentials;
import com.fincore.app.model.identity.PasswordHasher;
import com.fincore.app.model.shared.AuthException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    @Test
    public void testLogin() {
        AccountId stubId = mock(AccountId.class);
        String stubUsername = "TestUser";
        char[] emptyCharArray = {};
        CredentialStore stubCredStore = mock(CredentialStore.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        UUID stubUUID = UUID.randomUUID();
        when(stubCredStore.findByUsername(stubUsername))
                .thenReturn(
                        Optional.of(new Credentials(stubUsername, "random", stubId))
                );
        when(stubHasher.verify(emptyCharArray, "random")).thenReturn(true);
        when(stubId.idValue()).thenReturn(stubUUID);

        UUID result = null;
        try {
            result = new AuthService(stubCredStore, stubHasher).login(stubUsername, emptyCharArray);
        } catch (AuthException ignored) {}

        assertEquals(stubUUID, result);
    }

    @Test
    public void testNoUserLogin() {
        CredentialStore stubCredStore = mock(CredentialStore.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        when(stubCredStore.findByUsername(Mockito.any())).thenReturn(Optional.empty());

        AuthService authTest = new AuthService(stubCredStore, stubHasher);
        assertThrows(
                AuthException.class,
                () -> authTest.login("", new char[] {})
        );
    }

    @Test
    public void testWrongPasswordLogin() {
        CredentialStore stubCredStore = mock(CredentialStore.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        Credentials stubCredential = mock(Credentials.class);
        when(stubHasher.verify(Mockito.any(), Mockito.any())).thenReturn(false);
        when(stubCredStore.findByUsername(Mockito.any())).thenReturn(Optional.of(stubCredential));

        AuthService authTest = new AuthService(stubCredStore, stubHasher);
        assertThrows(
                AuthException.class,
                () -> authTest.login("", new char[] {})
        );
    }

    @Test
    public void testRegister() {
        // Test whether AuthService calls save?
        CredentialStore stubCredStore = mock(CredentialStore.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        AccountId stubId = new AccountId(UUID.randomUUID());

        AuthService testAuth = new AuthService(stubCredStore, stubHasher);
        testAuth.register("", new char[] {}, stubId);

        verify(stubCredStore, times(1)).save(Mockito.any());
        verify(stubHasher, times(1)).hash(Mockito.any());
    }

    @Test
    public void testRegisterExistingUser() {
        CredentialStore stubCredStore = mock(CredentialStore.class);
        PasswordHasher stubHasher = mock(PasswordHasher.class);
        Credentials stubCreds = mock(Credentials.class);
        Optional<Credentials> stubOptional = Optional.ofNullable(stubCreds);

        when(stubCredStore.findByUsername(Mockito.any())).thenReturn(stubOptional);

        AuthService testAuth = new AuthService(stubCredStore, stubHasher);

        assertThrows(
                AuthException.class,
                () -> testAuth.register("Test", new char[] {}, new AccountId(UUID.randomUUID()))
        );
    }
}
