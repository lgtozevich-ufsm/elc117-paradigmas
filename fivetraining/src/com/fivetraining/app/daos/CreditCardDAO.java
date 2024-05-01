package com.fivetraining.app.daos;

import com.fivetraining.app.models.CreditCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardDAO {
    private final Database database;

    public CreditCardDAO(Database database) {
        this.database = database;
    }

    public void insert(CreditCard creditCard) throws SQLException {
        String sql = "INSERT INTO credit_cards(number, expiry_date, cvv) VALUES (?, ?, ?)";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, creditCard.getNumber());
            statement.setString(2, creditCard.getExpiryDate());
            statement.setString(3, creditCard.getCvv());

            statement.executeUpdate();
        }
    }

    public void update(CreditCard creditCard) throws SQLException {
        String sql = "UPDATE credit_cards SET expiry_date = ?, cvv = ? WHERE number = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, creditCard.getExpiryDate());
            statement.setString(2, creditCard.getCvv());
            statement.setString(3, creditCard.getNumber());

            statement.executeUpdate();
        }
    }

    public void delete(String number) throws SQLException {
        String sql = "DELETE FROM credit_cards WHERE number = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, number);

            statement.executeUpdate();
        }
    }

    public CreditCard findByNumber(String number) throws SQLException {
        String sql = "SELECT number, expiry_date, cvv FROM credit_cards WHERE number = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, number);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    CreditCard creditCard = new CreditCard();
                    creditCard.setNumber(resultSet.getString("number"));
                    creditCard.setExpiryDate(resultSet.getString("expiry_date"));
                    creditCard.setCvv(resultSet.getString("cvv"));
                    return creditCard;
                }
            }
        }
        return null;
    }
}
