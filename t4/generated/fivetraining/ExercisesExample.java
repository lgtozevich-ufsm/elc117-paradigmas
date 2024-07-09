public class ExercisesExample {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

            ExercisesDAO dao = new ExercisesDAO(connection);

            Exercises example = insertExample(dao);
            updateExample(dao, example);
            getExample(dao, example.getCode());
            listExample(dao);
            deleteExample(dao, example);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
}

    public static Exercises insertExample(ExercisesDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Inserting example model...");

        Exercises model = new Exercises();
        model.setCode(12);
        model.setName("godffK6rlDhMK");
        model.setMuscles("49P2uZsgu");

        dao.insert(model);

        System.out.println("- " + model);

        return model;
    }

    public static void updateExample(ExercisesDAO dao, Exercises model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Updating example model...");

        model.setName("ovE");
        model.setMuscles("3xeDJgNcsclDqEvr");

        dao.update(model);

        System.out.println("- " + model);
    }

    public static void deleteExample(ExercisesDAO dao, Exercises model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Deleting example model...");

        dao.delete(model);
    }

    public static void getExample(ExercisesDAO dao, int code) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Getting example model...");

        Exercises model = dao.get(code);

        System.out.println("- " + model);
    }

    public static void listExample(ExercisesDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Listing example models...");

        java.util.List<Exercises> models = dao.list();

        for (Exercises model : models) {
            System.out.println("- " + model);
        }
    }
}
