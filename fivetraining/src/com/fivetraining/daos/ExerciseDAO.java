package com.fivetraining.daos;

import com.fivetraining.models.Exercise;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ExerciseDAO {
    private final Database database;

    public ExerciseDAO(Database database) {
        this.database = database;
    }

    public void insert(Exercise exercise) throws SQLException {
        String sql = "INSERT INTO exercises(code, name, muscles) VALUES (?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, exercise.getCode());
            statement.setString(2, exercise.getName());
            statement.setString(3, exercise.getMuscles());

            statement.executeUpdate();
        }
    }

    public void update(Exercise exercise) throws SQLException {
        String sql = "UPDATE exercises SET name = ?, muscles = ? WHERE code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, exercise.getName());
            statement.setString(2, exercise.getMuscles());
            statement.setInt(3, exercise.getCode());
        }
    }

    public void delete(Exercise exercise) throws SQLException {
        String sql = "DELETE FROM exercises WHERE code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, exercise.getCode());
        }
    }

    public Exercise findByCode(int code) throws SQLException {
        String sql = "SELECT (code, name, muscles) FROM exercises WHERE code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, code);

            try (ResultSet resultSet = statement.executeQuery()) {
                Exercise exercise = new Exercise();

                exercise.setCode(resultSet.getInt(1));
                exercise.setName(resultSet.getString(2));
                exercise.setMuscles(resultSet.getString(3));

                return exercise;
            }
        }
    }
}
