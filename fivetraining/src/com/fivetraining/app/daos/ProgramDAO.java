package com.fivetraining.app.daos;

import com.fivetraining.app.models.Program;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgramDAO {
    private final Database database;

    public ProgramDAO(Database database) {
        this.database = database;
    }

    public void insert(Program program) throws SQLException {
        String sql = "INSERT INTO programs(id, user_id, name) VALUES (?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, program.getId());
            statement.setInt(2, program.getUserId());
            statement.setString(3, program.getName());

            statement.executeUpdate();
        }
    }

    public void update(Program program) throws SQLException {
        String sql = "UPDATE programs SET name = ? WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, program.getName());
            statement.setInt(2, program.getId());

            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM programs WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public Program findById(int id) throws SQLException {
        String sql = "SELECT user_id, name FROM programs WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Program program = new Program();
                    program.setId(id);
                    program.setUserId(resultSet.getInt("user_id"));
                    program.setName(resultSet.getString("name"));
                    return program;
                }
            }
        }
        return null;
    }
}
