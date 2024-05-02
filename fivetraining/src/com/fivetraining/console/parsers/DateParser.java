package com.fivetraining.console.parsers;

import com.fivetraining.console.ConsoleArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser implements ConsoleParser {
    @Override
    public ConsoleArgument parse(String token) {
        return new ConsoleArgument(LocalDate.parse(token, DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
