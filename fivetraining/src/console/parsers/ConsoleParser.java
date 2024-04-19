package console.parsers;

import console.ConsoleArgument;

public interface ConsoleParser {
    public ConsoleArgument parse(String token);
}
