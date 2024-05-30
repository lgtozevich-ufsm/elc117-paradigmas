package com.fivetraining.app.commands;

import com.fivetraining.app.daos.PlanDAO;
import com.fivetraining.app.models.Plan;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.ConsoleParameter;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;

public class RegisterPlanCommand extends ConsoleCommand {
    private final PlanDAO planDAO;

    public RegisterPlanCommand(PlanDAO planDAO) {
        this.planDAO = planDAO;

        addParameter(ConsoleParameter.createInteger("código", true));
        addParameter(ConsoleParameter.createString("nome", true));
        addParameter(ConsoleParameter.createDouble("valor", true));
    }

    @Override
    public String getName() {
        return "reg-plano";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        int code = interaction.getArgument("código").asInteger();
        String name = interaction.getArgument("nome").asString();
        double price = interaction.getArgument("valor").asDouble();

        Plan plan = new Plan();
        plan.setCode(code);
        plan.setName(name);
        plan.setPrice(price);

        try {
            planDAO.insert(plan);

            interaction.getConsole().writeLine("O plano foi registrado com sucesso.");
            interaction.getConsole().writeLine();
            interaction.getConsole().writeLine("o  código: " + plan.getCode());
            interaction.getConsole().writeLine("|  nome: " + plan.getName());
            interaction.getConsole().writeLine("`- valor: " + plan.getPrice());
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
