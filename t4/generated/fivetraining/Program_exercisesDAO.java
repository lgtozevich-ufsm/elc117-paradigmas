public class Program_exercisesDAO {
    private final java.sql.Connection connection;

    public Program_exercisesDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void insert(Program_exercises model) throws java.sql.SQLException {
        String sql = "INSERT INTO `fivetraining`.`program_exercises`(`fivetraining`.`program_exercises`.`program_id`, `fivetraining`.`program_exercises`.`exercise_code`, `fivetraining`.`program_exercises`.`load`, `fivetraining`.`program_exercises`.`sets`, `fivetraining`.`program_exercises`.`minimum_repetitions`, `fivetraining`.`program_exercises`.`maximum_repetitions`, `fivetraining`.`program_exercises`.`resting_time`) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getProgram_id());
            statement.setInt(2, model.getExercise_code());
            statement.setInt(3, model.getLoad());
            statement.setInt(4, model.getSets());
            statement.setInt(5, model.getMinimum_repetitions());
            statement.setInt(6, model.getMaximum_repetitions());
            statement.setDouble(7, model.getResting_time());

            statement.executeUpdate();
        }
    }

    public void update(Program_exercises model) throws java.sql.SQLException {
        String sql = "UPDATE `fivetraining`.`program_exercises` SET `fivetraining`.`program_exercises`.`load` = ?, `fivetraining`.`program_exercises`.`sets` = ?, `fivetraining`.`program_exercises`.`minimum_repetitions` = ?, `fivetraining`.`program_exercises`.`maximum_repetitions` = ?, `fivetraining`.`program_exercises`.`resting_time` = ? WHERE `fivetraining`.`program_exercises`.`program_id` = ? AND `fivetraining`.`program_exercises`.`exercise_code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getLoad());
            statement.setInt(2, model.getSets());
            statement.setInt(3, model.getMinimum_repetitions());
            statement.setInt(4, model.getMaximum_repetitions());
            statement.setDouble(5, model.getResting_time());
            statement.setInt(6, model.getProgram_id());
            statement.setInt(7, model.getExercise_code());

            statement.executeUpdate();
        }
    }

    public void delete(Program_exercises model) throws java.sql.SQLException {
        String sql = "DELETE FROM `fivetraining`.`program_exercises` WHERE `fivetraining`.`program_exercises`.`program_id` = ? AND `fivetraining`.`program_exercises`.`exercise_code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getProgram_id());
            statement.setInt(2, model.getExercise_code());

            statement.executeUpdate();
        }
    }

    public Program_exercises get(int program_id, int exercise_code) throws java.sql.SQLException {
        String sql = "SELECT * FROM `fivetraining`.`program_exercises` WHERE `fivetraining`.`program_exercises`.`program_id` = ? AND `fivetraining`.`program_exercises`.`exercise_code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, program_id);
            statement.setInt(2, exercise_code);

            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return map(resultSet);
            }
        }

        return null;
    }

    public java.util.List<Program_exercises> list() throws java.sql.SQLException {
        java.util.List<Program_exercises> models = new java.util.ArrayList<>();
        String sql = "SELECT * FROM `fivetraining`.`program_exercises`";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                models.add(map(resultSet));
            }
        }

        return models;
    }

    private Program_exercises map(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Program_exercises model = new Program_exercises();
        model.setProgram_id(resultSet.getInt("program_id"));
        model.setExercise_code(resultSet.getInt("exercise_code"));
        model.setLoad(resultSet.getInt("load"));
        model.setSets(resultSet.getInt("sets"));
        model.setMinimum_repetitions(resultSet.getInt("minimum_repetitions"));
        model.setMaximum_repetitions(resultSet.getInt("maximum_repetitions"));
        model.setResting_time(resultSet.getDouble("resting_time"));

        return model;
    }
}
