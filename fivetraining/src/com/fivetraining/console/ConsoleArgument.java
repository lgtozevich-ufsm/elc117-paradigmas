package com.fivetraining.console;

public class ConsoleArgument {
    private final boolean filled;

    private final int integerValue;
    private final double doubleValue;
    private final boolean booleanValue;
    private final String stringValue;

    public ConsoleArgument() {
        filled = false;
        integerValue = 0;
        doubleValue = 0;
        booleanValue = false;
        stringValue = "";
    }

    public ConsoleArgument(int value) {
        filled = true;
        integerValue = value;
        doubleValue = value;
        booleanValue = false;
        stringValue = String.valueOf(value);
    }

    public ConsoleArgument(double value) {
        filled = true;
        integerValue = 0;
        doubleValue = value;
        booleanValue = false;
        stringValue = String.valueOf(value);
    }

    public ConsoleArgument(boolean value) {
        filled = true;
        integerValue = 0;
        doubleValue = 0;
        booleanValue = value;
        stringValue = String.valueOf(value);
    }

    public ConsoleArgument(String value) {
        filled = true;
        integerValue = 0;
        doubleValue = 0;
        booleanValue = false;
        stringValue = value;
    }

    public int asInteger() {
        return integerValue;
    }

    public double asDouble() {
        return doubleValue;
    }

    public boolean asBoolean() {
        return booleanValue;
    }

    public String asString() {
        return stringValue;
    }

    public boolean isFilled() {
        return filled;
    }
}
