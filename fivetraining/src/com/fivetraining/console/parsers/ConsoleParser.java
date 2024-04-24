package com.fivetraining.console.parsers;

import com.fivetraining.console.ConsoleArgument;

public interface ConsoleParser {
    public ConsoleArgument parse(String token);
}
