public class ExercisesDAO {
    private final java.sql.Connection connection;

    public ExercisesDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void insert(Exercises model) throws java.sql.SQLException {
        String sql = "INSERT INTO `fivetraining`.`exercises`(`fivetraining`.`exercises`.`code`, `fivetraining`.`exercises`.`name`, `fivetraining`.`exercises`.`muscles`) VALUES (?, ?, ?)";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getCode());
            statement.setString(2, model.getName());
            statement.setString(3, model.getMuscles());

            statement.executeUpdate();
        }
    }

    public void update(Exercises model) throws java.sql.SQLException {
        String sql = "UPDATE `fivetraining`.`exercises` SET `fivetraining`.`exercises`.`name` = ?, `fivetraining`.`exercises`.`muscles` = ? WHERE `fivetraining`.`exercises`.`code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, model.getName());
            statement.setString(2, model.getMuscles());
            statement.setInt(3, model.getCode());

            statement.executeUpdate();
        }
    }

    public void delete(Exercises model) throws java.sql.SQLException {
        String sql = "DELETE FROM `fivetraining`.`exercises` WHERE `fivetraining`.`exercises`.`code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getCode());

            statement.executeUpdate();
        }
    }

    public Exercises get(int code) throws java.sql.SQLException {
        String sql = "SELECT * FROM `fivetraining`.`exercises` WHERE `fivetraining`.`exercises`.`code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, code);

            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return map(resultSet);
            }
        }

        return null;
    }

    public java.util.List<Exercises> list() throws java.sql.SQLException {
        java.util.List<Exercises> models = new java.util.ArrayList<>();
        String sql = "SELECT * FROM `fivetraining`.`exercises`";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                models.add(map(resultSet));
            }
        }

        return models;
    }

    private Exercises map(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Exercises model = new Exercises();
        model.setCode(resultSet.getInt("code"));
        model.setName(resultSet.getString("name"));
        model.setMuscles(resultSet.getString("muscles"));

        return model;
    }
}
