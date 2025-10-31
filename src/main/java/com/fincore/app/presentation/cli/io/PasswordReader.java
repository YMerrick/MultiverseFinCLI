package com.fincore.app.presentation.cli.io;

public interface PasswordReader {
    char[] getPasswordInput(String prompt);
}
