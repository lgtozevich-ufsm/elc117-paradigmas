package com.fivetraining.daos;

import com.fivetraining.models.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Database database;

    public UserDAO(Database database) {
        this.database = database;
    }

    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO users(cpf, name, birth_date) VALUES (?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getCpf());
            statement.setString(2, user.getName());
            statement.setDate(3, Date.valueOf(user.getBirthDate()));

            statement.executeUpdate();
        }
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ? birth_date = ? WHERE cpf = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setDate(2, Date.valueOf(user.getBirthDate()));
            statement.setString(3, user.getCpf());
        }
    }

    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE cpf = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getCpf());
        }
    }

    public User findByCpf(String cpf) throws SQLException {
        String sql = "SELECT (cpf, name, birth_date) FROM users WHERE cpf = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, cpf);

            try (ResultSet resultSet = statement.executeQuery()) {
                User user = new User();

                user.setCpf(resultSet.getString(1));
                user.setName(resultSet.getString(2));
                user.setBirthDate(resultSet.getDate(3).toLocalDate());

                return user;
            }
        }
    }
}
