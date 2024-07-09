public class Workout_activitiesDAO {
    private final java.sql.Connection connection;

    public Workout_activitiesDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void insert(Workout_activities model) throws java.sql.SQLException {
        String sql = "INSERT INTO `fivetraining`.`workout_activities`(`fivetraining`.`workout_activities`.`workout_id`, `fivetraining`.`workout_activities`.`exercise_code`, `fivetraining`.`workout_activities`.`load`, `fivetraining`.`workout_activities`.`sets`, `fivetraining`.`workout_activities`.`minimum_repetitions`, `fivetraining`.`workout_activities`.`maximum_repetitions`, `fivetraining`.`workout_activities`.`resting_time`, `fivetraining`.`workout_activities`.`completed`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getWorkout_id());
            statement.setInt(2, model.getExercise_code());
            statement.setInt(3, model.getLoad());
            statement.setInt(4, model.getSets());
            statement.setInt(5, model.getMinimum_repetitions());
            statement.setInt(6, model.getMaximum_repetitions());
            statement.setDouble(7, model.getResting_time());
            statement.setBoolean(8, model.getCompleted());

            statement.executeUpdate();
        }
    }

    public void update(Workout_activities model) throws java.sql.SQLException {
        String sql = "UPDATE `fivetraining`.`workout_activities` SET `fivetraining`.`workout_activities`.`load` = ?, `fivetraining`.`workout_activities`.`sets` = ?, `fivetraining`.`workout_activities`.`minimum_repetitions` = ?, `fivetraining`.`workout_activities`.`maximum_repetitions` = ?, `fivetraining`.`workout_activities`.`resting_time` = ?, `fivetraining`.`workout_activities`.`completed` = ? WHERE `fivetraining`.`workout_activities`.`workout_id` = ? AND `fivetraining`.`workout_activities`.`exercise_code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getLoad());
            statement.setInt(2, model.getSets());
            statement.setInt(3, model.getMinimum_repetitions());
            statement.setInt(4, model.getMaximum_repetitions());
            statement.setDouble(5, model.getResting_time());
            statement.setBoolean(6, model.getCompleted());
            statement.setInt(7, model.getWorkout_id());
            statement.setInt(8, model.getExercise_code());

            statement.executeUpdate();
        }
    }

    public void delete(Workout_activities model) throws java.sql.SQLException {
        String sql = "DELETE FROM `fivetraining`.`workout_activities` WHERE `fivetraining`.`workout_activities`.`workout_id` = ? AND `fivetraining`.`workout_activities`.`exercise_code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getWorkout_id());
            statement.setInt(2, model.getExercise_code());

            statement.executeUpdate();
        }
    }

    public Workout_activities get(int workout_id, int exercise_code) throws java.sql.SQLException {
        String sql = "SELECT * FROM `fivetraining`.`workout_activities` WHERE `fivetraining`.`workout_activities`.`workout_id` = ? AND `fivetraining`.`workout_activities`.`exercise_code` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, workout_id);
            statement.setInt(2, exercise_code);

            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return map(resultSet);
            }
        }

        return null;
    }

    public java.util.List<Workout_activities> list() throws java.sql.SQLException {
        java.util.List<Workout_activities> models = new java.util.ArrayList<>();
        String sql = "SELECT * FROM `fivetraining`.`workout_activities`";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                models.add(map(resultSet));
            }
        }

        return models;
    }

    private Workout_activities map(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Workout_activities model = new Workout_activities();
        model.setWorkout_id(resultSet.getInt("workout_id"));
        model.setExercise_code(resultSet.getInt("exercise_code"));
        model.setLoad(resultSet.getInt("load"));
        model.setSets(resultSet.getInt("sets"));
        model.setMinimum_repetitions(resultSet.getInt("minimum_repetitions"));
        model.setMaximum_repetitions(resultSet.getInt("maximum_repetitions"));
        model.setResting_time(resultSet.getDouble("resting_time"));
        model.setCompleted(resultSet.getBoolean("completed"));

        return model;
    }
}
