package com.fivetraining.app.commands;

import com.fivetraining.app.daos.PlanDAO;
import com.fivetraining.app.models.Plan;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.util.List;

public class ListPlansCommand extends ConsoleCommand {
    private final PlanDAO planDAO;

    public ListPlansCommand(PlanDAO planDAO) {
        this.planDAO = planDAO;
    }

    @Override
    public String getName() {
        return "list-planos";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        try {
            List<Plan> plans = planDAO.findAll();

            interaction.getConsole().writeLine("Lista de planos");
            for (Plan plan : plans) {
                interaction.getConsole().writeLine("ID: " + plan.getId());
                interaction.getConsole().writeLine("Nome: " + plan.getName());
                interaction.getConsole().writeLine("Preço: " + plan.getPrice());
                interaction.getConsole().writeLine();
            }
        } catch (SQLException e) {
            throw new ConsoleCommandExecutionException("Erro ao listar planos: " + e.getMessage());
        }
    }
}
