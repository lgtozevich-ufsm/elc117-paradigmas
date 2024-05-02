package com.fivetraining.console.items;

public class ConsoleSeparator implements ConsoleDisplayable {
    private final String content;

    public ConsoleSeparator() {
        this.content = "";
    }

    public ConsoleSeparator(String content) {
        this.content = '\n' + content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String display() {
        return getContent();
    }
}
