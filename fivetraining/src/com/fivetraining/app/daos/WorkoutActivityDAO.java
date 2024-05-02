package com.fivetraining.app.daos;

import com.fivetraining.app.models.WorkoutActivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WorkoutActivityDAO {
    private final Database database;

    public WorkoutActivityDAO(Database database) {
        this.database = database;
    }

    public void insert(WorkoutActivity workoutActivity) throws SQLException {
        String sql = "INSERT INTO workout_activities(workout_id, exercise_code, load, sets, minimum_repetitions, maximum_repetitions, resting_time, completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workoutActivity.getWorkoutId());
            statement.setInt(2, workoutActivity.getExerciseCode());
            statement.setInt(3, workoutActivity.getLoad());
            statement.setInt(4, workoutActivity.getSets());
            statement.setInt(5, workoutActivity.getMinimumRepetitions());
            statement.setInt(6, workoutActivity.getMaximumRepetitions());
            statement.setDouble(7, workoutActivity.getRestingTime());
            statement.setBoolean(8, workoutActivity.isCompleted());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert workout activity");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                workoutActivity.setWorkoutId(generatedKeys.getInt(1));
            }
        }
    }

    public void update(WorkoutActivity workoutActivity) throws SQLException {
        String sql = "UPDATE workout_activities SET load = ?, sets = ?, minimum_repetitions = ?, maximum_repetitions = ?, resting_time = ?, completed = ? WHERE workout_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workoutActivity.getLoad());
            statement.setInt(2, workoutActivity.getSets());
            statement.setInt(3, workoutActivity.getMinimumRepetitions());
            statement.setInt(4, workoutActivity.getMaximumRepetitions());
            statement.setDouble(5, workoutActivity.getRestingTime());
            statement.setBoolean(6, workoutActivity.isCompleted());
            statement.setInt(7, workoutActivity.getWorkoutId());
            statement.setInt(8, workoutActivity.getExerciseCode());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to update workout activity");
            }
        }
    }

    public WorkoutActivity findByWorkoutIdAndExerciseCode(int workoutId, int exerciseCode) throws SQLException {
        String sql = "SELECT load, sets, minimum_repetitions, maximum_repetitions, resting_time, completed FROM workout_activities WHERE workout_id = ? AND exercise_code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workoutId);
            statement.setInt(2, exerciseCode);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            WorkoutActivity activity = new WorkoutActivity();
            activity.setWorkoutId(workoutId);
            activity.setExerciseCode(exerciseCode);
            activity.setLoad(resultSet.getInt("load"));
            activity.setSets(resultSet.getInt("sets"));
            activity.setMinimumRepetitions(resultSet.getInt("minimum_repetitions"));
            activity.setMaximumRepetitions(resultSet.getInt("maximum_repetitions"));
            activity.setRestingTime(resultSet.getDouble("resting_time"));
            activity.setCompleted(resultSet.getBoolean("completed"));

            return activity;
        }
    }

    public List<WorkoutActivity> findAllWithWorkoutId(int workoutId) throws SQLException {
        List<WorkoutActivity> activities = new ArrayList<>();
        String sql = "SELECT exercise_code, load, sets, minimum_repetitions, maximum_repetitions, resting_time, completed FROM workout_activities WHERE workout_id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workoutId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                WorkoutActivity activity = new WorkoutActivity();
                activity.setWorkoutId(workoutId);
                activity.setExerciseCode(resultSet.getInt("exercise_code"));
                activity.setLoad(resultSet.getInt("load"));
                activity.setSets(resultSet.getInt("sets"));
                activity.setMinimumRepetitions(resultSet.getInt("minimum_repetitions"));
                activity.setMaximumRepetitions(resultSet.getInt("maximum_repetitions"));
                activity.setRestingTime(resultSet.getDouble("resting_time"));
                activity.setCompleted(resultSet.getBoolean("completed"));

                activities.add(activity);
            }
        }

        return activities;
    }
}
