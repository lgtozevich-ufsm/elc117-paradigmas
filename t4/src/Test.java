import java.sql.*;

public class Test {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root", "password");

            DatabaseMetaData metaData = connection.getMetaData();

            System.out.println("Tables:");
            printResultSet(metaData.getTables("olympics", null, null, new String[] { "TABLE" }));

            System.out.println("Columns:");
            printResultSet(metaData.getColumns("olympics", null, "athletes", null));

            System.out.println("Primary keys:");
            printResultSet(metaData.getPrimaryKeys("olympics", null, "hosts"));
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        while (resultSet.next()) {
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                System.out.println("- `" + resultSetMetaData.getColumnName(i) + "` " + resultSetMetaData.getColumnTypeName(i) + " = " + resultSet.getString(i));
            }

            System.out.println();
        }
    }
}