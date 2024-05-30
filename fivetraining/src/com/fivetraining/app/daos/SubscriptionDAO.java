package com.fivetraining.app.daos;

import com.fivetraining.app.models.CreditCard;
import com.fivetraining.app.models.Subscription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO {
    private final Database database;

    public SubscriptionDAO(Database database) {
        this.database = database;
    }

    public void insert(Subscription subscription) throws SQLException {
        String sql = "INSERT INTO subscriptions(user_id, plan_code, start_date, end_date, card_number, card_cvv, card_expiry_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setInt(1, subscription.getUserId());
            statement.setInt(2, subscription.getPlanCode());
            statement.setDate(3, new java.sql.Date(subscription.getStartDate().getTime()));
            statement.setDate(4, new java.sql.Date(subscription.getEndDate().getTime()));
            statement.setString(5, subscription.getCreditCard().getNumber());
            statement.setString(6, subscription.getCreditCard().getCvv());
            statement.setString(7, subscription.getCreditCard().getExpiryDate());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert subscription");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                subscription.setId(generatedKeys.getInt(1));
            }
        }
    }

    public List<Subscription> findAll() throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT id, user_id, plan_code, start_date, end_date, card_number, card_cvv, card_expiry_date FROM subscriptions";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql))
        {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Subscription subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));
                subscription.setUserId(resultSet.getInt("user_id"));
                subscription.setPlanCode(resultSet.getInt("plan_code"));
                subscription.setStartDate(resultSet.getDate("start_date"));
                subscription.setEndDate(resultSet.getDate("end_date"));
                
                CreditCard creditCard = new CreditCard();
                creditCard.setNumber(resultSet.getString("card_number"));
                creditCard.setCvv(resultSet.getString("card_cvv"));
                creditCard.setExpiryDate(resultSet.getString("card_expiry_date"));

                subscription.setCreditCard(creditCard);
            
                subscriptions.add(subscription);
            }
        }

        return subscriptions;
    }
}
