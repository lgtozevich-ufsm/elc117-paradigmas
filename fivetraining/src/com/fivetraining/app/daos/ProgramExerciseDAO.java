package com.fivetraining.app.daos;

import com.fivetraining.app.models.ProgramExercise;
import com.fivetraining.app.models.User;

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
        String sql = "INSERT INTO program_exercises(exercise_code, load, sets, minimum_repetitions, maximum_repetitions, resting_time) VALUES ( ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, programExercise.getExerciseCode());
            statement.setInt(2, programExercise.getLoad());
            statement.setInt(3, programExercise.getSets());
            statement.setInt(4, programExercise.getMinimumRepetitions());
            statement.setInt(5, programExercise.getMaximumRepetitions());
            statement.setDouble(6, programExercise.getRestingTime());

            statement.executeUpdate();
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

            statement.executeUpdate();
        }
    }

    public void delete(int programId, int exerciseId) throws SQLException {
        String sql = "DELETE FROM program_exercises WHERE program_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, programId);
            statement.setInt(2, exerciseId);

            statement.executeUpdate();
        }
    }

    public ProgramExercise findByProgramAndExerciseId(int programId, int exerciseId) throws SQLException {
        String sql = "SELECT load, sets, minimum_repetitions, maximum_repetitions, resting_time FROM program_exercises WHERE program_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, programId);
            statement.setInt(2, exerciseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ProgramExercise programExercise = new ProgramExercise();
                    programExercise.setProgramId(programId);
                    programExercise.setExerciseCode(exerciseId);
                    programExercise.setLoad(resultSet.getInt("load"));
                    programExercise.setSets(resultSet.getInt("sets"));
                    programExercise.setMinimumRepetitions(resultSet.getInt("minimum_repetitions"));
                    programExercise.setMaximumRepetitions(resultSet.getInt("maximum_repetitions"));
                    programExercise.setRestingTime(resultSet.getDouble("resting_time"));
                    return programExercise;
                }
            }
        }

        return null;
    }

    public List<ProgramExercise> findAllByProgramId(int programId) throws SQLException {
        List<ProgramExercise> exercises = new ArrayList<>();
        String sql = "SELECT exercise_code, load, sets, minimum_repetitions, maximum_repetitions, resting_time FROM program_exercises WHERE program_id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, programId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProgramExercise exercise = new ProgramExercise();
                    exercise.setProgramId(programId);
                    exercise.setExerciseCode(resultSet.getInt("exercise_code"));
                    exercise.setLoad(resultSet.getInt("load"));
                    exercise.setSets(resultSet.getInt("sets"));
                    exercise.setMinimumRepetitions(resultSet.getInt("minimum_repetitions"));
                    exercise.setMaximumRepetitions(resultSet.getInt("maximum_repetitions"));
                    exercise.setRestingTime(resultSet.getDouble("resting_time"));
                    exercises.add(exercise);
                }
            }
        }

        return exercises;
    }
}


