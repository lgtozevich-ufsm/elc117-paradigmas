package com.fivetraining.console.exceptions;

public class ConsoleCommandExecutionException extends ConsoleCommandException {
    public ConsoleCommandExecutionException() {}

    public ConsoleCommandExecutionException(String message) {
        super(message);
    }

    public ConsoleCommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
