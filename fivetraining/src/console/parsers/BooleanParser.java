package console.parsers;

import console.ConsoleArgument;

public class BooleanParser implements ConsoleParser {
    @Override
    public ConsoleArgument parse(String token) {
        return new ConsoleArgument(token.equalsIgnoreCase("true"));
    }
}
