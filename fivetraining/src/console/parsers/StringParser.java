package console.parsers;

import console.ConsoleArgument;

public class StringParser implements ConsoleParser {
    @Override
    public ConsoleArgument parse(String token) {
        return new ConsoleArgument(token);
    }
}
