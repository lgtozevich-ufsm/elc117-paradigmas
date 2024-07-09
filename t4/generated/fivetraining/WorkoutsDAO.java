public class WorkoutsDAO {
    private final java.sql.Connection connection;

    public WorkoutsDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void insert(Workouts model) throws java.sql.SQLException {
        String sql = "INSERT INTO `fivetraining`.`workouts`(`fivetraining`.`workouts`.`id`, `fivetraining`.`workouts`.`user_id`, `fivetraining`.`workouts`.`program_name`, `fivetraining`.`workouts`.`start_time`, `fivetraining`.`workouts`.`end_time`) VALUES (?, ?, ?, ?, ?)";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
            statement.setInt(2, model.getUser_id());
            statement.setString(3, model.getProgram_name());
            statement.setTimestamp(4, model.getStart_time());
            statement.setTimestamp(5, model.getEnd_time());

            statement.executeUpdate();
        }
    }

    public void update(Workouts model) throws java.sql.SQLException {
        String sql = "UPDATE `fivetraining`.`workouts` SET `fivetraining`.`workouts`.`user_id` = ?, `fivetraining`.`workouts`.`program_name` = ?, `fivetraining`.`workouts`.`start_time` = ?, `fivetraining`.`workouts`.`end_time` = ? WHERE `fivetraining`.`workouts`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getUser_id());
            statement.setString(2, model.getProgram_name());
            statement.setTimestamp(3, model.getStart_time());
            statement.setTimestamp(4, model.getEnd_time());
            statement.setInt(5, model.getId());

            statement.executeUpdate();
        }
    }

    public void delete(Workouts model) throws java.sql.SQLException {
        String sql = "DELETE FROM `fivetraining`.`workouts` WHERE `fivetraining`.`workouts`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getId());

            statement.executeUpdate();
        }
    }

    public Workouts get(int id) throws java.sql.SQLException {
        String sql = "SELECT * FROM `fivetraining`.`workouts` WHERE `fivetraining`.`workouts`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return map(resultSet);
            }
        }

        return null;
    }

    public java.util.List<Workouts> list() throws java.sql.SQLException {
        java.util.List<Workouts> models = new java.util.ArrayList<>();
        String sql = "SELECT * FROM `fivetraining`.`workouts`";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                models.add(map(resultSet));
            }
        }

        return models;
    }

    private Workouts map(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Workouts model = new Workouts();
        model.setId(resultSet.getInt("id"));
        model.setUser_id(resultSet.getInt("user_id"));
        model.setProgram_name(resultSet.getString("program_name"));
        model.setStart_time(resultSet.getTimestamp("start_time"));
        model.setEnd_time(resultSet.getTimestamp("end_time"));

        return model;
    }
}
