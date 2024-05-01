package com.fivetraining.console.exceptions;

public class ConsoleCommandNotFoundException extends ConsoleCommandException {
    public ConsoleCommandNotFoundException() {}

    public ConsoleCommandNotFoundException(String message) {
        super(message);
    }

    public ConsoleCommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
