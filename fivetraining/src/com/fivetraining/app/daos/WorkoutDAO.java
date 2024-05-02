package com.fivetraining.app.daos;

import com.fivetraining.app.models.Workout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutDAO {
    private final Database database;

    public WorkoutDAO(Database database) {
        this.database = database;
    }

    public void insert(Workout workout) throws SQLException {
        String sql = "INSERT INTO workouts( user_id, program_id, start_time, end_time) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workout.getUserId());
            statement.setInt(2, workout.getProgramId());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(workout.getStartTime()));
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(workout.getEndTime()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert plan");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    workout.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void update(Workout workout) throws SQLException {
        String sql = "UPDATE workouts SET start_time = ?, end_time = ? WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(workout.getStartTime()));
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(workout.getEndTime()));
            statement.setInt(3, workout.getId());

            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM workouts WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public Workout findById(int id) throws SQLException {
        String sql = "SELECT user_id, program_id, start_time, end_time FROM workouts WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Workout workout = new Workout();
                    workout.setId(id);
                    workout.setUserId(resultSet.getInt("user_id"));
                    workout.setProgramId(resultSet.getInt("program_id"));
                    workout.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                    workout.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());
                    return workout;
                }
            }
        }
        return null;
    }
}
