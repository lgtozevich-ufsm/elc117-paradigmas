package com.fivetraining.app.daos;

import com.fivetraining.app.models.ProgramExercise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramExerciseDAO {
    private final Database database;

    public ProgramExerciseDAO(Database database) {
        this.database = database;
    }

    public void insert(ProgramExercise programExercise) throws SQLException {
        String sql = "INSERT INTO program_exercises(program_id,exercise_code, load, sets, minimum_repetitions, maximum_repetitions, resting_time) VALUES ( ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, programExercise.getProgramId());
            statement.setInt(2, programExercise.getExerciseCode());
            statement.setInt(3, programExercise.getLoad());
            statement.setInt(4, programExercise.getSets());
            statement.setInt(5, programExercise.getMinimumRepetitions());
            statement.setInt(6, programExercise.getMaximumRepetitions());
            statement.setDouble(7, programExercise.getRestingTime());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert program exercise");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                programExercise.setProgramId(generatedKeys.getInt(1));
            }
        }
    }

    public void update(ProgramExercise programExercise) throws SQLException {
        String sql = "UPDATE program_exercises SET load = ?, sets = ?, minimum_repetitions = ?, maximum_repetitions = ?, resting_time = ? WHERE program_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, programExercise.getLoad());
            statement.setInt(2, programExercise.getSets());
            statement.setInt(3, programExercise.getMinimumRepetitions());
            statement.setInt(4, programExercise.getMaximumRepetitions());
            statement.setDouble(5, programExercise.getRestingTime());
            statement.setInt(6, programExercise.getProgramId());
            statement.setInt(7, programExercise.getExerciseCode());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to update program exercise");
            }
        }
    }

    public void delete(ProgramExercise programExercise) throws SQLException {
        String sql = "DELETE FROM program_exercises WHERE program_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, programExercise.getProgramId());
            statement.setInt(2, programExercise.getExerciseCode());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to delete program exercise");
            }
        }
    }

    public ProgramExercise findByProgramIdAndExerciseCode(int programId, int exerciseCode) throws SQLException {
        String sql = "SELECT load, sets, minimum_repetitions, maximum_repetitions, resting_time FROM program_exercises WHERE program_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, programId);
            statement.setInt(2, exerciseCode);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            ProgramExercise programExercise = new ProgramExercise();
            programExercise.setProgramId(programId);
            programExercise.setExerciseCode(exerciseCode);
            programExercise.setLoad(resultSet.getInt("load"));
            programExercise.setSets(resultSet.getInt("sets"));
            programExercise.setMinimumRepetitions(resultSet.getInt("minimum_repetitions"));
            programExercise.setMaximumRepetitions(resultSet.getInt("maximum_repetitions"));
            programExercise.setRestingTime(resultSet.getDouble("resting_time"));

            return programExercise;
        }
    }

    public List<ProgramExercise> findAllWithProgramId(int programId) throws SQLException {
        List<ProgramExercise> exercises = new ArrayList<>();
        String sql = "SELECT exercise_code, load, sets, minimum_repetitions, maximum_repetitions, resting_time FROM program_exercises WHERE program_id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, programId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ProgramExercise programExercise = new ProgramExercise();
                programExercise.setProgramId(programId);
                programExercise.setExerciseCode(resultSet.getInt("exercise_code"));
                programExercise.setLoad(resultSet.getInt("load"));
                programExercise.setSets(resultSet.getInt("sets"));
                programExercise.setMinimumRepetitions(resultSet.getInt("minimum_repetitions"));
                programExercise.setMaximumRepetitions(resultSet.getInt("maximum_repetitions"));
                programExercise.setRestingTime(resultSet.getDouble("resting_time"));

                exercises.add(programExercise);
            }
        }

        return exercises;
    }
}


