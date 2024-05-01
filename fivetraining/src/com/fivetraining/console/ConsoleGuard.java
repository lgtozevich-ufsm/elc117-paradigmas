package com.fivetraining.console;

import com.fivetraining.console.exceptions.ConsoleCommandException;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.exceptions.ConsoleCommandNotFoundException;
import com.fivetraining.console.exceptions.ConsoleSyntaxException;
import com.fivetraining.console.items.ConsoleCommand;
import com.fivetraining.console.items.ConsoleDisplayable;

import java.util.*;

public class ConsoleGuard {
    private final Console console;
    private final ArrayList<ConsoleDisplayable> items;

    public ConsoleGuard(Console console) {
        this.console = console;
        this.items = new ArrayList<>();
    }

    public void addItem(ConsoleDisplayable items) {
        this.items.add(items);
    }

    public List<ConsoleDisplayable> getItems() {
        return Collections.unmodifiableList(items);
    }

    public ConsoleCommand getCommand(String name) {
        for (ConsoleDisplayable item : items) {
            if (item instanceof ConsoleCommand command) {
                if (command.getName().equals(name)) {
                    return command;
                }
            }
        }

        return null;
    }

    public void execute(String line) throws ConsoleSyntaxException, ConsoleCommandException {
        ConsoleTokenizer tokenizer = new ConsoleTokenizer(line);

        String name = tokenizer.nextToken();
        List<String> tokens = new ArrayList<>();

        while (tokenizer.hasNextToken()) {
            tokens.add(tokenizer.nextToken());
        }

        ConsoleCommand command = getCommand(name);

        if (command == null) {
            throw new ConsoleCommandNotFoundException("No such command: \"" + name + "\"");
        }

        List<ConsoleParameter> parameters = command.getParameters();
        Map<String, ConsoleArgument> arguments = new HashMap<>();

        for (int i = 0; i < parameters.size(); i++) {
            String token = i < tokens.size() ? tokens.get(i) : null;
            ConsoleParameter parameter = parameters.get(i);

            arguments.put(parameter.getName(), parameter.parse(token));
        }

        command.run(new ConsoleInteraction(console, arguments));
    }
}
