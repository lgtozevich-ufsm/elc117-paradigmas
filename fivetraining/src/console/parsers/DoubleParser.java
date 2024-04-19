package console.parsers;

import console.ConsoleArgument;

public class DoubleParser implements ConsoleParser {
    @Override
    public ConsoleArgument parse(String token) {
        return new ConsoleArgument(Double.parseDouble(token));
    }
}
