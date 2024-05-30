package com.fivetraining.app.daos;

import com.fivetraining.app.models.Plan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    private final Database database;

    public PlanDAO(Database database) {
        this.database = database;
    }

    public void insert(Plan plan) throws SQLException {
        String sql = "INSERT INTO plans(code, name, price) VALUES (?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, plan.getCode());
            statement.setString(2, plan.getName());
            statement.setDouble(3, plan.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert plan");
            }
        }
    }

    public Plan findByCode(int id) throws SQLException {
        String sql = "SELECT code, name, price FROM plans WHERE code = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Plan plan = new Plan();
            plan.setCode(resultSet.getInt("code"));
            plan.setName(resultSet.getString("name"));
            plan.setPrice(resultSet.getDouble("price"));

            return plan;
        }
    }

    public List<Plan> findAll() throws SQLException {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT code, name, price FROM plans";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Plan plan = new Plan();
                plan.setCode(resultSet.getInt("code"));
                plan.setName(resultSet.getString("name"));
                plan.setPrice(resultSet.getDouble("price"));

                plans.add(plan);
            }
        }

        return plans;
    }
}
