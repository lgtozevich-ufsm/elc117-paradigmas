package com.fivetraining.console.parsers;

import com.fivetraining.console.ConsoleArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser implements ConsoleParser {
    @Override
    public ConsoleArgument parse(String token) {
        return new ConsoleArgument(LocalDateTime.parse(token, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}
