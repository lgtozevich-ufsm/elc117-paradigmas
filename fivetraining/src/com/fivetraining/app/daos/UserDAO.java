package com.fivetraining.app.daos;

import com.fivetraining.app.models.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final Database database;

    public UserDAO(Database database) {
        this.database = database;
    }

    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO users(cpf, name, birth_date) VALUES (?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getCPF());
            statement.setString(2, user.getName());
            statement.setDate(3, Date.valueOf(user.getBirthDate()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert user");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        }
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET cpf = ?, name = ?, birth_date = ? WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getCPF());
            statement.setString(2, user.getName());
            statement.setDate(3, Date.valueOf(user.getBirthDate()));
            statement.setInt(4, user.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to update user");
            }
        }
    }

    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, user.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to delete user");
            }
        }
    }

    public User findByCPF(String cpf) throws SQLException {
        String sql = "SELECT id, cpf, name, birth_date FROM users WHERE cpf = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, cpf);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setCPF(resultSet.getString(2));
            user.setName(resultSet.getString(3));
            user.setBirthDate(resultSet.getDate(4).toLocalDate());

            return user;
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, cpf, name, birth_date FROM users";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setCPF(resultSet.getString("cpf"));
                user.setName(resultSet.getString("name"));
                user.setBirthDate(resultSet.getDate("birth_date").toLocalDate());

                users.add(user);
            }
        }

        return users;
    }

    public List<User> findAllWithPartialCPF(String cpf) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, cpf, name, birth_date FROM users WHERE CHARINDEX(LOWER(?), LOWER(cpf)) > 0";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql))
        {
            statement.setString(1, cpf);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setCPF(resultSet.getString("cpf"));
                user.setName(resultSet.getString("name"));
                user.setBirthDate(resultSet.getDate("birth_date").toLocalDate());

                users.add(user);
            }
        }

        return users;
    }

    public List<User> findAllWithPartialName(String name) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, cpf, name, birth_date FROM users WHERE CHARINDEX(LOWER(?), LOWER(name)) > 0";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql))
        {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setCPF(resultSet.getString("cpf"));
                user.setName(resultSet.getString("name"));
                user.setBirthDate(resultSet.getDate("birth_date").toLocalDate());

                users.add(user);
            }
        }

        return users;
    }
}
