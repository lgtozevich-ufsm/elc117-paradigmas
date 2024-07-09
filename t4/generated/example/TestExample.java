public class TestExample {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

            TestDAO dao = new TestDAO(connection);

            Test example = insertExample(dao);
            updateExample(dao, example);
            getExample(dao, example.getId());
            listExample(dao);
            deleteExample(dao, example);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
}

    public static Test insertExample(TestDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Inserting example model...");

        Test model = new Test();
        model.setTestTinyInt((byte)19);
        model.setTestBoolean(false);
        model.setTestSmallInt((short)19);
        model.setTestMediumInt(81);
        model.setTestInt(49);
        model.setTestBigInt(7L);
        model.setTestDecimal(new java.math.BigDecimal("58.89"));
        model.setTestFloat(6.96f);
        model.setTestFloat27(0.05);
        model.setTestDouble(7.27);
        model.setTestDate(new java.sql.Date(327898800000L));
        model.setTestDateTime(new java.sql.Timestamp(32321667000L));
        model.setTestTimestamp(new java.sql.Timestamp(1118613051000L));
        model.setTestTime(new java.sql.Time(7130005301792705921L));
        model.setTestChar("i");
        model.setTestVarChar("YpRpP");
        model.setTestTinyText("OOdjD35RE6");
        model.setTestMediumText("T3O6FScp");
        model.setTestLongText("lB6HruBn3Ir XKLb");
        model.setTestText(" D8ougZR32");

        dao.insert(model);

        System.out.println("- " + model);

        return model;
    }

    public static void updateExample(TestDAO dao, Test model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Updating example model...");

        model.setTestTinyInt((byte)66);
        model.setTestBoolean(false);
        model.setTestSmallInt((short)64);
        model.setTestMediumInt(47);
        model.setTestInt(92);
        model.setTestBigInt(68L);
        model.setTestDecimal(new java.math.BigDecimal("89.34"));
        model.setTestFloat(5.45f);
        model.setTestFloat27(6.87);
        model.setTestDouble(7.17);
        model.setTestDate(new java.sql.Date(1149303600000L));
        model.setTestDateTime(new java.sql.Timestamp(1386805536000L));
        model.setTestTimestamp(new java.sql.Timestamp(1499979617000L));
        model.setTestTime(new java.sql.Time(725591319816223045L));
        model.setTestChar("N");
        model.setTestVarChar("UIF8");
        model.setTestTinyText("zrQpppFXza4fDshy");
        model.setTestMediumText("MEboT t7fjKWUA2");
        model.setTestLongText("2FV5");
        model.setTestText("wy AfZarj");

        dao.update(model);

        System.out.println("- " + model);
    }

    public static void deleteExample(TestDAO dao, Test model) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Deleting example model...");

        dao.delete(model);
    }

    public static void getExample(TestDAO dao, int id) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Getting example model...");

        Test model = dao.get(id);

        System.out.println("- " + model);
    }

    public static void listExample(TestDAO dao) throws java.sql.SQLException {
        System.out.println();
        System.out.println("Listing example models...");

        java.util.List<Test> models = dao.list();

        for (Test model : models) {
            System.out.println("- " + model);
        }
    }
}
