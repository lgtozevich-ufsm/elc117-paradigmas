public class Program_exercisesExample {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

            Program_exercisesDAO dao = new Program_exercisesDAO(connection);

            Program_exercises example = insertExample(dao);
            updateExample(dao, example);
            getExample(dao, example.getProgram_id(), example.getExercise_code());
            listExample(dao);
            deleteExample(dao, example);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
}

    public static Program_exercises insertExample(Program_exercisesDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Inserting example model...");

        Program_exercises model = new Program_exercises();
        model.setProgram_id(71);
        model.setExercise_code(94);
        model.setLoad(69);
        model.setSets(70);
        model.setMinimum_repetitions(79);
        model.setMaximum_repetitions(98);
        model.setResting_time(5.17);

        dao.insert(model);

        System.out.println("- " + model);

        return model;
    }

    public static void updateExample(Program_exercisesDAO dao, Program_exercises model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Updating example model...");

        model.setLoad(96);
        model.setSets(64);
        model.setMinimum_repetitions(7);
        model.setMaximum_repetitions(61);
        model.setResting_time(4.75);

        dao.update(model);

        System.out.println("- " + model);
    }

    public static void deleteExample(Program_exercisesDAO dao, Program_exercises model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Deleting example model...");

        dao.delete(model);
    }

    public static void getExample(Program_exercisesDAO dao, int program_id, int exercise_code) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Getting example model...");

        Program_exercises model = dao.get(program_id, exercise_code);

        System.out.println("- " + model);
    }

    public static void listExample(Program_exercisesDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Listing example models...");

        java.util.List<Program_exercises> models = dao.list();

        for (Program_exercises model : models) {
            System.out.println("- " + model);
        }
    }
}
