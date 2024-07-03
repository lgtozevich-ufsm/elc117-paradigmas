import database.Scheme;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root", "password");

            DatabaseMetaData metaData = connection.getMetaData();

            Scheme scheme = Scheme.extractSchemeFromCatalog(metaData, "olympics");

            System.out.println(scheme);


        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}