package com.fivetraining.console.exceptions;

public class ConsoleExecutionException extends Exception {
    public ConsoleExecutionException() {

    }

    public ConsoleExecutionException(String message) {
        super(message);
    }

    public ConsoleExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
