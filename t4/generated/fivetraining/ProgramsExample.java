public class ProgramsExample {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

            ProgramsDAO dao = new ProgramsDAO(connection);

            Programs example = insertExample(dao);
            updateExample(dao, example);
            getExample(dao, example.getId());
            listExample(dao);
            deleteExample(dao, example);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
}

    public static Programs insertExample(ProgramsDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Inserting example model...");

        Programs model = new Programs();
        model.setId(67);
        model.setUser_id(62);
        model.setName("HAfQg");

        dao.insert(model);

        System.out.println("- " + model);

        return model;
    }

    public static void updateExample(ProgramsDAO dao, Programs model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Updating example model...");

        model.setUser_id(54);
        model.setName("fU2SGrbhbEJU2d");

        dao.update(model);

        System.out.println("- " + model);
    }

    public static void deleteExample(ProgramsDAO dao, Programs model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Deleting example model...");

        dao.delete(model);
    }

    public static void getExample(ProgramsDAO dao, int id) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Getting example model...");

        Programs model = dao.get(id);

        System.out.println("- " + model);
    }

    public static void listExample(ProgramsDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Listing example models...");

        java.util.List<Programs> models = dao.list();

        for (Programs model : models) {
            System.out.println("- " + model);
        }
    }
}
