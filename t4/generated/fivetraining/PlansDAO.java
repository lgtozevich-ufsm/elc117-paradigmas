public class PlansDAO {
    private final java.sql.Connection connection;

    public PlansDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void insert(Plans model) throws java.sql.SQLException {
        String sql = "INSERT INTO `fivetraining`.`plans`(`fivetraining`.`plans`.`code`, `fivetraining`.`plans`.`name`, `fivetraining`.`plans`.`price`) VALUES (?, ?, ?)";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getCode());
            statement.setString(2, model.getName());
            statement.setDouble(3, model.getPrice());

            statement.executeUpdate();
        }
    }

    public void update(Plans model) throws java.sql.SQLException {
        String sql = "UPDATE `fivetraining`.`plans` SET `fivetraining`.`plans`.`name` = ?, `fivetraining`.`plans`.`price` = ? WHERE `fivetraining`.`plans`.`code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, model.getName());
            statement.setDouble(2, model.getPrice());
            statement.setInt(3, model.getCode());

            statement.executeUpdate();
        }
    }

    public void delete(Plans model) throws java.sql.SQLException {
        String sql = "DELETE FROM `fivetraining`.`plans` WHERE `fivetraining`.`plans`.`code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getCode());

            statement.executeUpdate();
        }
    }

    public Plans get(int code) throws java.sql.SQLException {
        String sql = "SELECT * FROM `fivetraining`.`plans` WHERE `fivetraining`.`plans`.`code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, code);

            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return map(resultSet);
            }
        }

        return null;
    }

    public java.util.List<Plans> list() throws java.sql.SQLException {
        java.util.List<Plans> models = new java.util.ArrayList<>();
        String sql = "SELECT * FROM `fivetraining`.`plans`";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                models.add(map(resultSet));
            }
        }

        return models;
    }

    private Plans map(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Plans model = new Plans();
        model.setCode(resultSet.getInt("code"));
        model.setName(resultSet.getString("name"));
        model.setPrice(resultSet.getDouble("price"));

        return model;
    }
}
