package com.fivetraining.console.exceptions;

public class ConsoleCommandException extends Exception {
    public ConsoleCommandException() {}

    public ConsoleCommandException(String message) {
        super(message);
    }

    public ConsoleCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
