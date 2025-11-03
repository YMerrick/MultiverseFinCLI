package com.fincore.app.domain.shared;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
