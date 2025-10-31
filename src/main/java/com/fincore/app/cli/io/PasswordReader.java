package com.fincore.app.cli.io;

public interface PasswordReader {
    char[] getPasswordInput(String prompt);
}
