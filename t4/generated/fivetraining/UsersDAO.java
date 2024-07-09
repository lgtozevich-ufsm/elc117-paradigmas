public class UsersDAO {
    private final java.sql.Connection connection;

    public UsersDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void insert(Users model) throws java.sql.SQLException {
        String sql = "INSERT INTO `fivetraining`.`users`(`fivetraining`.`users`.`id`, `fivetraining`.`users`.`cpf`, `fivetraining`.`users`.`name`, `fivetraining`.`users`.`birth_date`) VALUES (?, ?, ?, ?)";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
            statement.setString(2, model.getCpf());
            statement.setString(3, model.getName());
            statement.setDate(4, model.getBirth_date());

            statement.executeUpdate();
        }
    }

    public void update(Users model) throws java.sql.SQLException {
        String sql = "UPDATE `fivetraining`.`users` SET `fivetraining`.`users`.`cpf` = ?, `fivetraining`.`users`.`name` = ?, `fivetraining`.`users`.`birth_date` = ? WHERE `fivetraining`.`users`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, model.getCpf());
            statement.setString(2, model.getName());
            statement.setDate(3, model.getBirth_date());
            statement.setInt(4, model.getId());

            statement.executeUpdate();
        }
    }

    public void delete(Users model) throws java.sql.SQLException {
        String sql = "DELETE FROM `fivetraining`.`users` WHERE `fivetraining`.`users`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getId());

            statement.executeUpdate();
        }
    }

    public Users get(int id) throws java.sql.SQLException {
        String sql = "SELECT * FROM `fivetraining`.`users` WHERE `fivetraining`.`users`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return map(resultSet);
            }
        }

        return null;
    }

    public java.util.List<Users> list() throws java.sql.SQLException {
        java.util.List<Users> models = new java.util.ArrayList<>();
        String sql = "SELECT * FROM `fivetraining`.`users`";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                models.add(map(resultSet));
            }
        }

        return models;
    }

    private Users map(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Users model = new Users();
        model.setId(resultSet.getInt("id"));
        model.setCpf(resultSet.getString("cpf"));
        model.setName(resultSet.getString("name"));
        model.setBirth_date(resultSet.getDate("birth_date"));

        return model;
    }
}
