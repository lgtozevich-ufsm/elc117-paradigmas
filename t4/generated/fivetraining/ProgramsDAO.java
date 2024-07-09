public class ProgramsDAO {
    private final java.sql.Connection connection;

    public ProgramsDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void insert(Programs model) throws java.sql.SQLException {
        String sql = "INSERT INTO `fivetraining`.`programs`(`fivetraining`.`programs`.`id`, `fivetraining`.`programs`.`user_id`, `fivetraining`.`programs`.`name`) VALUES (?, ?, ?)";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
            statement.setInt(2, model.getUser_id());
            statement.setString(3, model.getName());

            statement.executeUpdate();
        }
    }

    public void update(Programs model) throws java.sql.SQLException {
        String sql = "UPDATE `fivetraining`.`programs` SET `fivetraining`.`programs`.`user_id` = ?, `fivetraining`.`programs`.`name` = ? WHERE `fivetraining`.`programs`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getUser_id());
            statement.setString(2, model.getName());
            statement.setInt(3, model.getId());

            statement.executeUpdate();
        }
    }

    public void delete(Programs model) throws java.sql.SQLException {
        String sql = "DELETE FROM `fivetraining`.`programs` WHERE `fivetraining`.`programs`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getId());

            statement.executeUpdate();
        }
    }

    public Programs get(int id) throws java.sql.SQLException {
        String sql = "SELECT * FROM `fivetraining`.`programs` WHERE `fivetraining`.`programs`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return map(resultSet);
            }
        }

        return null;
    }

    public java.util.List<Programs> list() throws java.sql.SQLException {
        java.util.List<Programs> models = new java.util.ArrayList<>();
        String sql = "SELECT * FROM `fivetraining`.`programs`";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                models.add(map(resultSet));
            }
        }

        return models;
    }

    private Programs map(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Programs model = new Programs();
        model.setId(resultSet.getInt("id"));
        model.setUser_id(resultSet.getInt("user_id"));
        model.setName(resultSet.getString("name"));

        return model;
    }
}
