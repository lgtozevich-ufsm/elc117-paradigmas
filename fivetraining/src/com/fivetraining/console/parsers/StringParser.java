package com.fivetraining.console.parsers;

import com.fivetraining.console.ConsoleArgument;

public class StringParser implements ConsoleParser {
    @Override
    public ConsoleArgument parse(String token) {
        return new ConsoleArgument(token);
    }
}
