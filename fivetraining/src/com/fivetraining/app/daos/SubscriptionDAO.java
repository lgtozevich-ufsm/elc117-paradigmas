package com.fivetraining.app.daos;

import com.fivetraining.app.models.CreditCard;
import com.fivetraining.app.models.Subscription;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionDAO {
    private final Database database;

    public SubscriptionDAO(Database database) {
        this.database = database;
    }

    public void insert(Subscription subscription) throws SQLException {
        String sql = "INSERT INTO subscriptions(user_id, plan_id, start_date, end_date, card_number,card_cvv, card_expiry_date ) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {

            statement.setInt(1, subscription.getUserId());
            statement.setInt(2, subscription.getPlanId());
            statement.setDate(3, new java.sql.Date(subscription.getStartDate().getTime()));
            statement.setDate(4, new java.sql.Date(subscription.getEndDate().getTime()));
            statement.setString(5, subscription.getCreditCard().getNumber());
            statement.setString(6, subscription.getCreditCard().getCvv());
            statement.setString(7, subscription.getCreditCard().getExpiryDate());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert plan");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subscription.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void update(Subscription subscription) throws SQLException {
        String sql = "UPDATE subscriptions SET start_date = ?, end_date = ? WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(subscription.getStartDate().getTime()));
            statement.setDate(2, new java.sql.Date(subscription.getEndDate().getTime()));
            statement.setInt(3, subscription.getId());

            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM subscriptions WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }


}
