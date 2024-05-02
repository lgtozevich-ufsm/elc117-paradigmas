package com.fivetraining.app.daos;

import com.fivetraining.app.models.WorkoutActivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class WorkoutActivityDAO {
    private final Database database;

    public WorkoutActivityDAO(Database database) {
        this.database = database;
    }

    public void insert(WorkoutActivity workoutActivity) throws SQLException {
        String sql = "INSERT INTO workout_activities(exercise_code, load, sets, minimum_repetitions, maximum_repetitions, resting_time, completed_date_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workoutActivity.getExerciseCode());
            statement.setInt(2, workoutActivity.getLoad());
            statement.setInt(3, workoutActivity.getSets());
            statement.setInt(4, workoutActivity.getMinimumRepetitions());
            statement.setInt(5, workoutActivity.getMaximumRepetitions());
            statement.setDouble(6, workoutActivity.getRestingTime());
            statement.setTimestamp(7, Timestamp.valueOf(workoutActivity.getCompletedDateTime()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert plan");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    workoutActivity.setWorkoutId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void update(WorkoutActivity workoutActivity) throws SQLException {
        String sql = "UPDATE workout_activities SET load = ?, sets = ?, minimum_repetitions = ?, maximum_repetitions = ?, resting_time = ?, completed_date_time = ? WHERE workout_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workoutActivity.getLoad());
            statement.setInt(2, workoutActivity.getSets());
            statement.setInt(3, workoutActivity.getMinimumRepetitions());
            statement.setInt(4, workoutActivity.getMaximumRepetitions());
            statement.setDouble(5, workoutActivity.getRestingTime());
            statement.setTimestamp(6, Timestamp.valueOf(workoutActivity.getCompletedDateTime()));
            statement.setInt(7, workoutActivity.getWorkoutId());
            statement.setInt(8, workoutActivity.getExerciseCode());

            statement.executeUpdate();
        }
    }

    public void delete(int workoutId, int exerciseId) throws SQLException {
        String sql = "DELETE FROM workout_activities WHERE workout_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workoutId);
            statement.setInt(2, exerciseId);

            statement.executeUpdate();
        }
    }

    public WorkoutActivity findByWorkoutAndExerciseId(int workoutId, int exerciseId) throws SQLException {
        String sql = "SELECT load, sets, minimum_repetitions, maximum_repetitions, resting_time, completed_date_time FROM workout_activities WHERE workout_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workoutId);
            statement.setInt(2, exerciseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    WorkoutActivity workoutActivity = new WorkoutActivity();
                    workoutActivity.setWorkoutId(workoutId);
                    workoutActivity.setExerciseCode(exerciseId);
                    workoutActivity.setLoad(resultSet.getInt("load"));
                    workoutActivity.setSets(resultSet.getInt("sets"));
                    workoutActivity.setMinimumRepetitions(resultSet.getInt("minimum_repetitions"));
                    workoutActivity.setMaximumRepetitions(resultSet.getInt("maximum_repetitions"));
                    workoutActivity.setRestingTime(resultSet.getDouble("resting_time"));
                    workoutActivity.setCompletedDateTime(resultSet.getTimestamp("completed_date_time").toLocalDateTime());
                    return workoutActivity;
                }
            }
        }
        return null;
    }
}
