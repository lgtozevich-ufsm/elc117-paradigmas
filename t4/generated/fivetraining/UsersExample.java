public class UsersExample {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

            UsersDAO dao = new UsersDAO(connection);

            Users example = insertExample(dao);
            updateExample(dao, example);
            getExample(dao, example.getId());
            listExample(dao);
            deleteExample(dao, example);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
}

    public static Users insertExample(UsersDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Inserting example model...");

        Users model = new Users();
        model.setId(88);
        model.setCpf("oqVOsF");
        model.setName("PQDMcjqUlbMPL");
        model.setBirth_date(new java.sql.Date(645764400000L));

        dao.insert(model);

        System.out.println("- " + model);

        return model;
    }

    public static void updateExample(UsersDAO dao, Users model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Updating example model...");

        model.setCpf("u");
        model.setName("tAzrQebqZy319MQ");
        model.setBirth_date(new java.sql.Date(1164074400000L));

        dao.update(model);

        System.out.println("- " + model);
    }

    public static void deleteExample(UsersDAO dao, Users model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Deleting example model...");

        dao.delete(model);
    }

    public static void getExample(UsersDAO dao, int id) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Getting example model...");

        Users model = dao.get(id);

        System.out.println("- " + model);
    }

    public static void listExample(UsersDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Listing example models...");

        java.util.List<Users> models = dao.list();

        for (Users model : models) {
            System.out.println("- " + model);
        }
    }
}
