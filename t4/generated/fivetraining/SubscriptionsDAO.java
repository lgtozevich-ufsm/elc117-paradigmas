public class SubscriptionsDAO {
    private final java.sql.Connection connection;

    public SubscriptionsDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void insert(Subscriptions model) throws java.sql.SQLException {
        String sql = "INSERT INTO `fivetraining`.`subscriptions`(`fivetraining`.`subscriptions`.`id`, `fivetraining`.`subscriptions`.`user_id`, `fivetraining`.`subscriptions`.`plan_code`, `fivetraining`.`subscriptions`.`start_date`, `fivetraining`.`subscriptions`.`end_date`, `fivetraining`.`subscriptions`.`card_number`, `fivetraining`.`subscriptions`.`card_expiry_date`, `fivetraining`.`subscriptions`.`card_cvv`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
            statement.setInt(2, model.getUser_id());
            statement.setInt(3, model.getPlan_code());
            statement.setDate(4, model.getStart_date());
            statement.setDate(5, model.getEnd_date());
            statement.setString(6, model.getCard_number());
            statement.setDate(7, model.getCard_expiry_date());
            statement.setString(8, model.getCard_cvv());

            statement.executeUpdate();
        }
    }

    public void update(Subscriptions model) throws java.sql.SQLException {
        String sql = "UPDATE `fivetraining`.`subscriptions` SET `fivetraining`.`subscriptions`.`user_id` = ?, `fivetraining`.`subscriptions`.`plan_code` = ?, `fivetraining`.`subscriptions`.`start_date` = ?, `fivetraining`.`subscriptions`.`end_date` = ?, `fivetraining`.`subscriptions`.`card_number` = ?, `fivetraining`.`subscriptions`.`card_expiry_date` = ?, `fivetraining`.`subscriptions`.`card_cvv` = ? WHERE `fivetraining`.`subscriptions`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getUser_id());
            statement.setInt(2, model.getPlan_code());
            statement.setDate(3, model.getStart_date());
            statement.setDate(4, model.getEnd_date());
            statement.setString(5, model.getCard_number());
            statement.setDate(6, model.getCard_expiry_date());
            statement.setString(7, model.getCard_cvv());
            statement.setInt(8, model.getId());

            statement.executeUpdate();
        }
    }

    public void delete(Subscriptions model) throws java.sql.SQLException {
        String sql = "DELETE FROM `fivetraining`.`subscriptions` WHERE `fivetraining`.`subscriptions`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getId());

            statement.executeUpdate();
        }
    }

    public Subscriptions get(int id) throws java.sql.SQLException {
        String sql = "SELECT * FROM `fivetraining`.`subscriptions` WHERE `fivetraining`.`subscriptions`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return map(resultSet);
            }
        }

        return null;
    }

    public java.util.List<Subscriptions> list() throws java.sql.SQLException {
        java.util.List<Subscriptions> models = new java.util.ArrayList<>();
        String sql = "SELECT * FROM `fivetraining`.`subscriptions`";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                models.add(map(resultSet));
            }
        }

        return models;
    }

    private Subscriptions map(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Subscriptions model = new Subscriptions();
        model.setId(resultSet.getInt("id"));
        model.setUser_id(resultSet.getInt("user_id"));
        model.setPlan_code(resultSet.getInt("plan_code"));
        model.setStart_date(resultSet.getDate("start_date"));
        model.setEnd_date(resultSet.getDate("end_date"));
        model.setCard_number(resultSet.getString("card_number"));
        model.setCard_expiry_date(resultSet.getDate("card_expiry_date"));
        model.setCard_cvv(resultSet.getString("card_cvv"));

        return model;
    }
}
