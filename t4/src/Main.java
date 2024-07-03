import database.Scheme;
import engine.Resource;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.templates.DAOTemplate;
import engine.templates.ModelTemplate;
import engine.templates.Template;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Template> templates = new ArrayList<>();
        templates.add(new ModelTemplate());
        templates.add(new DAOTemplate());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root", "password");

            DatabaseMetaData metaData = connection.getMetaData();

            Scheme scheme = Scheme.extractSchemeFromCatalog(metaData, "olympics");

            TemplateStandard standard = new TemplateStandard();

            for (var table : scheme.tables()) {
                TemplateSettings settings = new TemplateSettings(table);

                for (Template template : templates) {
                    save(template.render(standard, settings));
                }
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void save(Resource resource) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resource.name()))) {
            writer.write(resource.content());
        }
    }
}