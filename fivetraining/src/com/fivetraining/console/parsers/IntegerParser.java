package com.fivetraining.console.parsers;

import com.fivetraining.console.ConsoleArgument;

public class IntegerParser implements ConsoleParser {
    @Override
    public ConsoleArgument parse(String token) {
        return new ConsoleArgument(Integer.parseInt(token));
    }
}
