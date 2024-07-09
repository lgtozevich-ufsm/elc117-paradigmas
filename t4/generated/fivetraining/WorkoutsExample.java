public class WorkoutsExample {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

            WorkoutsDAO dao = new WorkoutsDAO(connection);

            Workouts example = insertExample(dao);
            updateExample(dao, example);
            getExample(dao, example.getId());
            listExample(dao);
            deleteExample(dao, example);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
}

    public static Workouts insertExample(WorkoutsDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Inserting example model...");

        Workouts model = new Workouts();
        model.setId(77);
        model.setUser_id(52);
        model.setProgram_name("EEOlT0");
        model.setStart_time(new java.sql.Timestamp(656644762000L));
        model.setEnd_time(new java.sql.Timestamp(1450933447000L));

        dao.insert(model);

        System.out.println("- " + model);

        return model;
    }

    public static void updateExample(WorkoutsDAO dao, Workouts model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Updating example model...");

        model.setUser_id(85);
        model.setProgram_name("Cvb9X83Vm5oJoqSS");
        model.setStart_time(new java.sql.Timestamp(14858846000L));
        model.setEnd_time(new java.sql.Timestamp(52802899000L));

        dao.update(model);

        System.out.println("- " + model);
    }

    public static void deleteExample(WorkoutsDAO dao, Workouts model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Deleting example model...");

        dao.delete(model);
    }

    public static void getExample(WorkoutsDAO dao, int id) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Getting example model...");

        Workouts model = dao.get(id);

        System.out.println("- " + model);
    }

    public static void listExample(WorkoutsDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Listing example models...");

        java.util.List<Workouts> models = dao.list();

        for (Workouts model : models) {
            System.out.println("- " + model);
        }
    }
}
