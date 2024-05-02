package com.fivetraining.app.daos;

import com.fivetraining.app.models.Exercise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert plan");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exercise.setCode(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Exercise findByCode(int code) throws SQLException {
        String sql = "SELECT code, name, muscles FROM exercises WHERE code = ?";

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

    public List<Exercise> findAll() throws SQLException {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT code, name, muscles FROM exercises";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setCode(resultSet.getInt("code"));
                exercise.setName(resultSet.getString("name"));
                exercise.setMuscles(resultSet.getString("muscles"));

                exercises.add(exercise);
            }
        }

        return exercises;
    }
}
