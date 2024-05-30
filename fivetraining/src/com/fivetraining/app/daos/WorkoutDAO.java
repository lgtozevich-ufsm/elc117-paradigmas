package com.fivetraining.app.daos;

import com.fivetraining.app.models.Workout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDAO {
    private final Database database;

    public WorkoutDAO(Database database) {
        this.database = database;
    }

    public void insert(Workout workout) throws SQLException {
        String sql = "INSERT INTO workouts(user_id, program_name, start_time, end_time) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workout.getUserId());
            statement.setString(2, workout.getProgramName());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(workout.getStartTime()));
            statement.setTimestamp(4, workout.getEndTime() == null ? null : java.sql.Timestamp.valueOf(workout.getEndTime()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert workout");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                workout.setId(generatedKeys.getInt(1));
            }
        }
    }

    public void update(Workout workout) throws SQLException {
        String sql = "UPDATE workouts SET user_id = ?, program_name = ?, start_time = ?, end_time = ? WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, workout.getUserId());
            statement.setString(2, workout.getProgramName());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(workout.getStartTime()));
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(workout.getEndTime()));
            statement.setInt(5, workout.getId());

            statement.executeUpdate();

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to update workout");
            }
        }
    }

    public Workout findUnfinishedByUserId(int userId) throws SQLException {
        String sql = "SELECT id, user_id, program_name, start_time, end_time FROM workouts WHERE user_id = ? AND end_time is NULL";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Workout workout = new Workout();
            workout.setId(resultSet.getInt("id"));
            workout.setUserId(resultSet.getInt("user_id"));
            workout.setProgramName(resultSet.getString("program_name"));
            workout.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
            workout.setEndTime(null);

            return workout;
        }
    }

    public List<Workout> findAllWithUserId(int userId) throws SQLException {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT id, program_name, start_time, end_time FROM workouts WHERE user_id = ? ORDER BY start_time";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Workout workout = new Workout();
                workout.setId(resultSet.getInt("id"));
                workout.setUserId(userId);
                workout.setProgramName(resultSet.getString("program_name"));
                workout.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                workout.setEndTime(resultSet.getTimestamp("end_time") == null ? null : resultSet.getTimestamp("end_time").toLocalDateTime());

                workouts.add(workout);
            }
        }

        return workouts;
    }

    public List<Workout> findAllWithUserIdInRange(int userId, LocalDateTime fromTime, LocalDateTime toTime) throws SQLException {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT id, program_name, start_time, end_time FROM workouts WHERE user_id = ? AND start_time BETWEEN ? AND ? ORDER BY start_time";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(fromTime));
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(toTime));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Workout workout = new Workout();
                workout.setId(resultSet.getInt("id"));
                workout.setUserId(userId);
                workout.setProgramName(resultSet.getString("program_name"));
                workout.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                workout.setEndTime(resultSet.getTimestamp("end_time") == null ? null : resultSet.getTimestamp("end_time").toLocalDateTime());
                workouts.add(workout);
            }
        }

        return workouts;
    }
}
