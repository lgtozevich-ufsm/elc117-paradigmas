package com.fivetraining.console.exceptions;

public class ConsoleCommandParsingException extends ConsoleCommandException {
    public ConsoleCommandParsingException() {}

    public ConsoleCommandParsingException(String message) {
        super(message);
    }

    public ConsoleCommandParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
