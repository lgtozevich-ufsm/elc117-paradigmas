public class TestDAO {
    private final java.sql.Connection connection;

    public TestDAO(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void insert(Test model) throws java.sql.SQLException {
        String sql = "INSERT INTO `example`.`test`(`example`.`test`.`id`, `example`.`test`.`testTinyInt`, `example`.`test`.`testBoolean`, `example`.`test`.`testSmallInt`, `example`.`test`.`testMediumInt`, `example`.`test`.`testInt`, `example`.`test`.`testBigInt`, `example`.`test`.`testDecimal`, `example`.`test`.`testFloat`, `example`.`test`.`testFloat27`, `example`.`test`.`testDouble`, `example`.`test`.`testDate`, `example`.`test`.`testDateTime`, `example`.`test`.`testTimestamp`, `example`.`test`.`testTime`, `example`.`test`.`testChar`, `example`.`test`.`testVarChar`, `example`.`test`.`testTinyText`, `example`.`test`.`testMediumText`, `example`.`test`.`testLongText`, `example`.`test`.`testText`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
            statement.setByte(2, model.getTestTinyInt());
            statement.setBoolean(3, model.getTestBoolean());
            statement.setShort(4, model.getTestSmallInt());
            statement.setInt(5, model.getTestMediumInt());
            statement.setInt(6, model.getTestInt());
            statement.setLong(7, model.getTestBigInt());
            statement.setBigDecimal(8, model.getTestDecimal());
            statement.setFloat(9, model.getTestFloat());
            statement.setDouble(10, model.getTestFloat27());
            statement.setDouble(11, model.getTestDouble());
            statement.setDate(12, model.getTestDate());
            statement.setTimestamp(13, model.getTestDateTime());
            statement.setTimestamp(14, model.getTestTimestamp());
            statement.setTime(15, model.getTestTime());
            statement.setString(16, model.getTestChar());
            statement.setString(17, model.getTestVarChar());
            statement.setString(18, model.getTestTinyText());
            statement.setString(19, model.getTestMediumText());
            statement.setString(20, model.getTestLongText());
            statement.setString(21, model.getTestText());

            statement.executeUpdate();

            try (java.sql.ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    model.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void update(Test model) throws java.sql.SQLException {
        String sql = "UPDATE `example`.`test` SET `example`.`test`.`testTinyInt` = ?, `example`.`test`.`testBoolean` = ?, `example`.`test`.`testSmallInt` = ?, `example`.`test`.`testMediumInt` = ?, `example`.`test`.`testInt` = ?, `example`.`test`.`testBigInt` = ?, `example`.`test`.`testDecimal` = ?, `example`.`test`.`testFloat` = ?, `example`.`test`.`testFloat27` = ?, `example`.`test`.`testDouble` = ?, `example`.`test`.`testDate` = ?, `example`.`test`.`testDateTime` = ?, `example`.`test`.`testTimestamp` = ?, `example`.`test`.`testTime` = ?, `example`.`test`.`testChar` = ?, `example`.`test`.`testVarChar` = ?, `example`.`test`.`testTinyText` = ?, `example`.`test`.`testMediumText` = ?, `example`.`test`.`testLongText` = ?, `example`.`test`.`testText` = ? WHERE `example`.`test`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setByte(1, model.getTestTinyInt());
            statement.setBoolean(2, model.getTestBoolean());
            statement.setShort(3, model.getTestSmallInt());
            statement.setInt(4, model.getTestMediumInt());
            statement.setInt(5, model.getTestInt());
            statement.setLong(6, model.getTestBigInt());
            statement.setBigDecimal(7, model.getTestDecimal());
            statement.setFloat(8, model.getTestFloat());
            statement.setDouble(9, model.getTestFloat27());
            statement.setDouble(10, model.getTestDouble());
            statement.setDate(11, model.getTestDate());
            statement.setTimestamp(12, model.getTestDateTime());
            statement.setTimestamp(13, model.getTestTimestamp());
            statement.setTime(14, model.getTestTime());
            statement.setString(15, model.getTestChar());
            statement.setString(16, model.getTestVarChar());
            statement.setString(17, model.getTestTinyText());
            statement.setString(18, model.getTestMediumText());
            statement.setString(19, model.getTestLongText());
            statement.setString(20, model.getTestText());
            statement.setInt(21, model.getId());

            statement.executeUpdate();
        }
    }

    public void delete(Test model) throws java.sql.SQLException {
        String sql = "DELETE FROM `example`.`test` WHERE `example`.`test`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, model.getId());

            statement.executeUpdate();
        }
    }

    public Test get(int id) throws java.sql.SQLException {
        String sql = "SELECT * FROM `example`.`test` WHERE `example`.`test`.`id` = ?";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return map(resultSet);
            }
        }

        return null;
    }

    public java.util.List<Test> list() throws java.sql.SQLException {
        java.util.List<Test> models = new java.util.ArrayList<>();
        String sql = "SELECT * FROM `example`.`test`";

        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                models.add(map(resultSet));
            }
        }

        return models;
    }

    private Test map(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Test model = new Test();
        model.setId(resultSet.getInt("id"));
        model.setTestTinyInt(resultSet.getByte("testTinyInt"));
        model.setTestBoolean(resultSet.getBoolean("testBoolean"));
        model.setTestSmallInt(resultSet.getShort("testSmallInt"));
        model.setTestMediumInt(resultSet.getInt("testMediumInt"));
        model.setTestInt(resultSet.getInt("testInt"));
        model.setTestBigInt(resultSet.getLong("testBigInt"));
        model.setTestDecimal(resultSet.getBigDecimal("testDecimal"));
        model.setTestFloat(resultSet.getFloat("testFloat"));
        model.setTestFloat27(resultSet.getDouble("testFloat27"));
        model.setTestDouble(resultSet.getDouble("testDouble"));
        model.setTestDate(resultSet.getDate("testDate"));
        model.setTestDateTime(resultSet.getTimestamp("testDateTime"));
        model.setTestTimestamp(resultSet.getTimestamp("testTimestamp"));
        model.setTestTime(resultSet.getTime("testTime"));
        model.setTestChar(resultSet.getString("testChar"));
        model.setTestVarChar(resultSet.getString("testVarChar"));
        model.setTestTinyText(resultSet.getString("testTinyText"));
        model.setTestMediumText(resultSet.getString("testMediumText"));
        model.setTestLongText(resultSet.getString("testLongText"));
        model.setTestText(resultSet.getString("testText"));

        return model;
    }
}
