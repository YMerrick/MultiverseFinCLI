package com.fincore.app.model.shared;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
