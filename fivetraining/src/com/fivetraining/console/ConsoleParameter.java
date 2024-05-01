package com.fivetraining.console;

import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.exceptions.ConsoleCommandParsingException;
import com.fivetraining.console.parsers.*;

public class ConsoleParameter {
    private final ConsoleParser parser;
    private final String type;

    private final String name;
    private final boolean required;

    private ConsoleParameter(ConsoleParser parser, String type, String name, boolean required) {
        this.parser = parser;
        this.type = type;
        this.name = name;
        this.required = required;
    }

    public ConsoleArgument parse(String token) throws ConsoleCommandParsingException {
        if (token == null || token.isEmpty()) {
            if (isRequired()) {
                throw new ConsoleCommandParsingException("Required argument \"" + name + "\" is not filled");
            }

            return new ConsoleArgument();
        }

        try {
            return parser.parse(token);
        } catch (Exception exception) {
            throw new ConsoleCommandParsingException("Invalid argument \"" + name + "\": " + exception.toString());
        }
    }

    public String getName() {
        return this.name;
    }

    public boolean isRequired() {
        return this.required;
    }

    @Override
    public String toString() {
        String fullName = this.name + ":" + this.type;

        return isRequired() ? "<" + fullName + ">" : "[" + fullName + "]";
    }

    public static ConsoleParameter createInteger(String name, boolean required) {
        return new ConsoleParameter(new IntegerParser(), "int", name, required);
    }

    public static ConsoleParameter createDouble(String name, boolean required) {
        return new ConsoleParameter(new DoubleParser(), "float", name, required);
    }

    public static ConsoleParameter createBoolean(String name, boolean required) {
        return new ConsoleParameter(new BooleanParser(), "(true/false)", name, required);
    }

    public static ConsoleParameter createString(String name, boolean required) {
        return new ConsoleParameter(new StringParser(), "str", name, required);
    }
}
