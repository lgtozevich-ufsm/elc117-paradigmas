package com.fivetraining.console;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConsoleArgument {
    private final boolean filled;

    private final int integerValue;
    private final double doubleValue;
    private final boolean booleanValue;
    private final String stringValue;
    private final LocalDateTime dateTimeValue;

    public ConsoleArgument() {
        filled = false;
        integerValue = 0;
        doubleValue = 0;
        booleanValue = false;
        stringValue = "";
        dateTimeValue = LocalDateTime.MIN;
    }

    public ConsoleArgument(int value) {
        filled = true;
        integerValue = value;
        doubleValue = value;
        booleanValue = false;
        stringValue = String.valueOf(value);
        dateTimeValue = LocalDateTime.MIN;
    }

    public ConsoleArgument(double value) {
        filled = true;
        integerValue = 0;
        doubleValue = value;
        booleanValue = false;
        stringValue = String.valueOf(value);
        dateTimeValue = LocalDateTime.MIN;
    }

    public ConsoleArgument(boolean value) {
        filled = true;
        integerValue = 0;
        doubleValue = 0;
        booleanValue = value;
        stringValue = String.valueOf(value);
        dateTimeValue = LocalDateTime.MIN;
    }

    public ConsoleArgument(String value) {
        filled = true;
        integerValue = 0;
        doubleValue = 0;
        booleanValue = false;
        stringValue = value;
        dateTimeValue = LocalDateTime.MIN;
    }

    public ConsoleArgument(LocalDate value) {
        filled = true;
        integerValue = 0;
        doubleValue = 0;
        booleanValue = false;
        stringValue = String.valueOf(value);
        dateTimeValue = value.atTime(0, 0);
    }

    public ConsoleArgument(LocalDateTime value) {
        filled = true;
        integerValue = 0;
        doubleValue = 0;
        booleanValue = false;
        stringValue = String.valueOf(value);
        dateTimeValue = value;
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

    public LocalDate asDate() {
        return dateTimeValue.toLocalDate();
    }

    public LocalDateTime asDateTime() {
        return dateTimeValue;
    }

    public boolean isFilled() {
        return filled;
    }
}
