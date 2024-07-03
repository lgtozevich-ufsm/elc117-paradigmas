import database.Scheme;
import database.Table;
import engine.Resource;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.templates.DAOTemplate;
import engine.templates.ExampleTemplate;
import engine.templates.ModelTemplate;
import engine.templates.Template;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";

        if (args.length < 5) {
            System.out.println("Usage: <url> <user> <password> <database> <outputDirectory>");
            return;
        }

        String url = args[0];
        String user = args[1];
        String password = args[2];
        String database = args[3];
        Path output = Path.of(args[4]);

        List<Template> templates = new ArrayList<>();
        templates.add(new ModelTemplate());
        templates.add(new DAOTemplate());
        templates.add(new ExampleTemplate());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            DatabaseMetaData metaData = connection.getMetaData();
            Scheme scheme = Scheme.extractSchemeFromCatalog(metaData, database);

            TemplateStandard standard = new TemplateStandard();

            for (Table table : scheme.tables()) {
                TemplateSettings settings = new TemplateSettings(driver, url, user, password, table);

                for (Template template : templates) {
                    save(template.render(standard, settings), output);
                }
            }

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void save(Resource resource, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path.resolve(resource.name()))) {
            writer.write(resource.content());
        }
    }
}