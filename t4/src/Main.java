import database.Scheme;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.templates.DAOTemplate;
import engine.templates.ModelTemplate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root", "pass");

            DatabaseMetaData metaData = connection.getMetaData();

            Scheme scheme = Scheme.extractSchemeFromCatalog(metaData, "olympics");

            TemplateStandard standard = new TemplateStandard();

            for (var table : scheme.tables()) {
                TemplateSettings settings = new TemplateSettings(table);

                String modelContent = new ModelTemplate().render(standard, settings);
                //String daoContent = new DAOTemplate().render(standard, settings);
                //String exampleContent = new ExampleTemplate().render(standard, settings);

                writeToFile(standard.getModelTypeName(table) + ".java", modelContent);
                //writeToFile(standard.getDAOTypeName(table) + ".java", daoContent);
                //writeToFile(standard.getModelTypeName(table) + "Example.java", exampleContent);
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}