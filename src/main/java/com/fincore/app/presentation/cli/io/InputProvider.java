package com.fincore.app.presentation.cli.io;

public interface InputProvider {
    String getStringInput(String prompt);

    default int getIntInput(String prompt) {
        String line;
        for (int i = 0;; i++) {
            try {
                line = getStringInput(generatePrompt(prompt, i));
                return Integer.parseInt(line);
            } catch (NumberFormatException ignored) {}
        }
    }

    default double getDoubleInput(String prompt) {
        String line;
        for (int i = 0;; i++) {
            try {
                line = getStringInput(generatePrompt(prompt, i));
                return Double.parseDouble(line);
            } catch (NumberFormatException ignored) {}
        }
    }

    private String generatePrompt(String prompt, int i) {
        return i < 1 ?
                prompt:
                "Please try again: ";
    }
}
