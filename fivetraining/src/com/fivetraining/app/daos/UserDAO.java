package com.fivetraining.app.daos;

import com.fivetraining.app.models.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final Database database;

    public UserDAO(Database database) {
        this.database = database;
    }

    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO users(cpf, name, birth_date) VALUES (?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getCpf());
            statement.setString(2, user.getName());
            statement.setDate(3, Date.valueOf(user.getBirthDate()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert user");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ? birth_date = ? WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setDate(2, Date.valueOf(user.getBirthDate()));
            statement.setInt(3, user.getId());
        }
    }

    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, user.getId());
        }
    }

    public User findByCpf(String cpf) throws SQLException {
        String sql = "SELECT (id, cpf, name, birth_date) FROM users WHERE cpf = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, cpf);

            try (ResultSet resultSet = statement.executeQuery()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setCpf(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setBirthDate(resultSet.getDate(4).toLocalDate());

                return user;
            }
        }
    }

    public User findById(int id) throws SQLException {
        String sql = "SELECT (id, cpf, name, birth_date) FROM users WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setCpf(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setBirthDate(resultSet.getDate(4).toLocalDate());

                return user;
            }
        }
    }

}
