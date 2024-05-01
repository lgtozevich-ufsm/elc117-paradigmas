package com.fivetraining.daos;

import com.fivetraining.models.Subscription;

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
        String sql = "INSERT INTO subscriptions(id, user_id, plan_id, start_date, end_date, credit_card_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, subscription.getId());
            statement.setInt(2, subscription.getUserId());
            statement.setInt(3, subscription.getPlanId());
            statement.setDate(4, new java.sql.Date(subscription.getStartDate().getTime()));
            statement.setDate(5, new java.sql.Date(subscription.getEndDate().getTime()));
            statement.setString(6, subscription.getCreditCard().getNumber());

            statement.executeUpdate();
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

    public Subscription findById(int id) throws SQLException {
        String sql = "SELECT user_id, plan_id, start_date, end_date, credit_card_number FROM subscriptions WHERE id = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Subscription subscription = new Subscription();
                    subscription.setId(id);
                    subscription.setUserId(resultSet.getInt("user_id"));
                    subscription.setPlanId(resultSet.getInt("plan_id"));
                    subscription.setStartDate(resultSet.getDate("start_date"));
                    subscription.setEndDate(resultSet.getDate("end_date"));

                    CreditCardDAO creditCardDAO = new CreditCardDAO(database);
                    subscription.setCreditCard(creditCardDAO.findByNumber(resultSet.getString("credit_card_number")));

                    return subscription;
                }
            }
        }
        return null;
    }
}
