public class Workout_activitiesExample {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

            Workout_activitiesDAO dao = new Workout_activitiesDAO(connection);

            Workout_activities example = insertExample(dao);
            updateExample(dao, example);
            getExample(dao, example.getWorkout_id(), example.getExercise_code());
            listExample(dao);
            deleteExample(dao, example);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
}

    public static Workout_activities insertExample(Workout_activitiesDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Inserting example model...");

        Workout_activities model = new Workout_activities();
        model.setWorkout_id(74);
        model.setExercise_code(90);
        model.setLoad(59);
        model.setSets(21);
        model.setMinimum_repetitions(7);
        model.setMaximum_repetitions(34);
        model.setResting_time(9.65);
        model.setCompleted(true);

        dao.insert(model);

        System.out.println("- " + model);

        return model;
    }

    public static void updateExample(Workout_activitiesDAO dao, Workout_activities model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Updating example model...");

        model.setLoad(97);
        model.setSets(21);
        model.setMinimum_repetitions(56);
        model.setMaximum_repetitions(21);
        model.setResting_time(6.16);
        model.setCompleted(true);

        dao.update(model);

        System.out.println("- " + model);
    }

    public static void deleteExample(Workout_activitiesDAO dao, Workout_activities model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Deleting example model...");

        dao.delete(model);
    }

    public static void getExample(Workout_activitiesDAO dao, int workout_id, int exercise_code) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Getting example model...");

        Workout_activities model = dao.get(workout_id, exercise_code);

        System.out.println("- " + model);
    }

    public static void listExample(Workout_activitiesDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Listing example models...");

        java.util.List<Workout_activities> models = dao.list();

        for (Workout_activities model : models) {
            System.out.println("- " + model);
        }
    }
}
