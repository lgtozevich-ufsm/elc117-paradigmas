public class PlansExample {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

            PlansDAO dao = new PlansDAO(connection);

            Plans example = insertExample(dao);
            updateExample(dao, example);
            getExample(dao, example.getCode());
            listExample(dao);
            deleteExample(dao, example);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
}

    public static Plans insertExample(PlansDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Inserting example model...");

        Plans model = new Plans();
        model.setCode(99);
        model.setName("B1viiRu4q");
        model.setPrice(2.77);

        dao.insert(model);

        System.out.println("- " + model);

        return model;
    }

    public static void updateExample(PlansDAO dao, Plans model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Updating example model...");

        model.setName(" SVzVFqOsbqcAbH");
        model.setPrice(7.81);

        dao.update(model);

        System.out.println("- " + model);
    }

    public static void deleteExample(PlansDAO dao, Plans model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Deleting example model...");

        dao.delete(model);
    }

    public static void getExample(PlansDAO dao, int code) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Getting example model...");

        Plans model = dao.get(code);

        System.out.println("- " + model);
    }

    public static void listExample(PlansDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Listing example models...");

        java.util.List<Plans> models = dao.list();

        for (Plans model : models) {
            System.out.println("- " + model);
        }
    }
}
