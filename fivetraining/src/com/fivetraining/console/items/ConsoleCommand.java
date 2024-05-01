package com.fivetraining.console.items;

import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;

import java.util.ArrayList;
import java.util.List;

public abstract class ConsoleCommand implements ConsoleDisplayable {
    private final List<ConsoleParameter> parameters;

    protected ConsoleCommand() {
        this.parameters = new ArrayList<>();
    }

    public List<ConsoleParameter> getParameters() {
        return parameters;
    }

    public abstract String getName();
    public abstract void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException;

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder(getName());

        for (ConsoleParameter parameter : parameters) {
            builder.append(' ');
            builder.append(parameter);
        }

        return builder.toString();
    }

    protected void addParameter(ConsoleParameter parameter) {
        this.parameters.add(parameter);
    }
}
