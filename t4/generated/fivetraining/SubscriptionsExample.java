public class SubscriptionsExample {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

            SubscriptionsDAO dao = new SubscriptionsDAO(connection);

            Subscriptions example = insertExample(dao);
            updateExample(dao, example);
            getExample(dao, example.getId());
            listExample(dao);
            deleteExample(dao, example);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
}

    public static Subscriptions insertExample(SubscriptionsDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Inserting example model...");

        Subscriptions model = new Subscriptions();
        model.setId(22);
        model.setUser_id(16);
        model.setPlan_code(78);
        model.setStart_date(new java.sql.Date(698986800000L));
        model.setEnd_date(new java.sql.Date(1356573600000L));
        model.setCard_number("eF4FaobvovRyfp1");
        model.setCard_expiry_date(new java.sql.Date(483246000000L));
        model.setCard_cvv("l0Sy");

        dao.insert(model);

        System.out.println("- " + model);

        return model;
    }

    public static void updateExample(SubscriptionsDAO dao, Subscriptions model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Updating example model...");

        model.setUser_id(8);
        model.setPlan_code(30);
        model.setStart_date(new java.sql.Date(1439175600000L));
        model.setEnd_date(new java.sql.Date(1327543200000L));
        model.setCard_number("5FU8");
        model.setCard_expiry_date(new java.sql.Date(650948400000L));
        model.setCard_cvv("RpSYp");

        dao.update(model);

        System.out.println("- " + model);
    }

    public static void deleteExample(SubscriptionsDAO dao, Subscriptions model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Deleting example model...");

        dao.delete(model);
    }

    public static void getExample(SubscriptionsDAO dao, int id) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Getting example model...");

        Subscriptions model = dao.get(id);

        System.out.println("- " + model);
    }

    public static void listExample(SubscriptionsDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Listing example models...");

        java.util.List<Subscriptions> models = dao.list();

        for (Subscriptions model : models) {
            System.out.println("- " + model);
        }
    }
}
