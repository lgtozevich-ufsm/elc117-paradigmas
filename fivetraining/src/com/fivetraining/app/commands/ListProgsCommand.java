package com.fivetraining.app.commands;

import com.fivetraining.app.daos.ProgramDAO;
import com.fivetraining.app.daos.ProgramExerciseDAO;
import com.fivetraining.app.models.Program;
import com.fivetraining.app.models.ProgramExercise;
import com.fivetraining.console.ConsoleInteraction;
import com.fivetraining.console.exceptions.ConsoleCommandExecutionException;
import com.fivetraining.console.items.ConsoleCommand;

import java.sql.SQLException;
import java.util.List;

public class ListProgsCommand extends ConsoleCommand {
    private final ProgramDAO programDAO;
    private final ProgramExerciseDAO programExerciseDAO;

    public ListProgsCommand(ProgramDAO programDAO, ProgramExerciseDAO programExerciseDAO) {
        this.programDAO = programDAO;
        this.programExerciseDAO = programExerciseDAO;
    }

    @Override
    public String getName() {
        return "list-progs";
    }

    @Override
    public void run(ConsoleInteraction interaction) throws ConsoleCommandExecutionException {
        try {
            List<Program> programs = programDAO.findAll();

            for (Program program : programs) {
                interaction.getConsole().writeLine();
                interaction.getConsole().writeLine("o  id: " + program.getId());
                interaction.getConsole().writeLine("|  nome: " + program.getName());

                List<ProgramExercise> exercises = programExerciseDAO.findByProgramId(program.getId());

                for (ProgramExercise exercise : exercises) {
                    interaction.getConsole().writeLine("|  `- Código do Exercício: " + exercise.getExerciseCode());
                    interaction.getConsole().writeLine("|     - Séries: " + exercise.getSets());
                    interaction.getConsole().writeLine("|     - Repetições Mínimas: " + exercise.getMinimumRepetitions());
                    interaction.getConsole().writeLine("|     - Repetições Máximas: " + exercise.getMaximumRepetitions());
                    interaction.getConsole().writeLine("|     - Carga: " + exercise.getLoad());
                    interaction.getConsole().writeLine("|     - Tempo de Descanso: " + exercise.getRestingTime());
                }
            }
        } catch (SQLException exception) {
            throw new ConsoleCommandExecutionException(exception.getMessage());
        }
    }
}
