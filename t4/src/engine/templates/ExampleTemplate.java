package engine.templates;

import database.Column;
import database.Table;
import engine.Resource;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.TemplateUtils;
import engine.descriptors.TypeDescriptor;

import java.util.List;
import java.util.stream.Collectors;

public class ExampleTemplate implements Template {
    public Resource render(TemplateStandard standard, TemplateSettings settings) {
        StringBuilder builder = new StringBuilder();

        String driver = settings.driver();
        String url = settings.url();
        String user = settings.user();
        String password = settings.password();
        Table table = settings.table();

        builder.append("public class ");
        builder.append(standard.getExampleClassName(table));
        builder.append(" {\n");

        builder.append("    public static void main(String[] args) {\n");

        builder.append("        try {\n");

        builder.append("            System.out.println(\"Connecting to database...\");\n");

        builder.append("\n");
        builder.append("            Class.forName(");
        builder.append(TemplateUtils.escapeString(driver));
        builder.append(");\n");
        
        builder.append("            java.sql.Connection connection = java.sql.DriverManager.getConnection(");
        builder.append(TemplateUtils.escapeString(url));
        builder.append(", ");
        builder.append(TemplateUtils.escapeString(user));
        builder.append(", ");
        builder.append(TemplateUtils.escapeString(password));
        builder.append(");\n");

        builder.append("\n");
        builder.append("            ");
        builder.append(standard.getDAOClassName(table));
        builder.append(" dao = new ");
        builder.append(standard.getDAOClassName(table));
        builder.append("(connection);\n");

        builder.append("\n");
        builder.append("            ");
        builder.append(standard.getModelClassName(table));
        builder.append(" example = ");
        builder.append(standard.getExampleInsertMethodName(table));
        builder.append("(dao);\n");

        builder.append("            ");
        builder.append(standard.getExampleUpdateMethodName(table));
        builder.append("(dao, example);\n");

        builder.append("            ");
        builder.append(standard.getExampleGetMethodName(table));
        builder.append("(dao, ");
        builder.append(table.primaryKeyColumns().stream()
                .map(column -> "example." + standard.getModelGetterName(column) + "()")
                .collect(Collectors.joining(", ")));
        builder.append(");\n");

        builder.append("            ");
        builder.append(standard.getExampleListMethodName(table));
        builder.append("(dao);\n");

        builder.append("            ");
        builder.append(standard.getExampleDeleteMethodName(table));
        builder.append("(dao, example);\n");

        builder.append("\n");
        builder.append("            connection.close();\n");

        builder.append("        } catch (Exception exception) {\n");
        builder.append("            exception.printStackTrace();\n");
        builder.append("        }\n");
        builder.append("}\n");


        appendInsertExampleMethod(builder, standard, table);
        appendUpdateExampleMethod(builder, standard, table);
        appendDeleteExampleMethod(builder, standard, table);
        appendGetExampleMethod(builder, standard, table);
        appendListExampleMethod(builder, standard, table);

        builder.append("}\n");

        return new Resource(standard.getExampleClassName(settings.table()) + ".java", builder.toString());
    }


    private void appendInsertExampleMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> columns = table.baseColumns();

        builder.append("\n");
        builder.append("    public static ");
        builder.append(standard.getModelClassName(table));
        builder.append(" ");
        builder.append(standard.getExampleInsertMethodName(table));
        builder.append("(");
        builder.append(standard.getDAOClassName(table));
        builder.append(" dao) throws java.sql.SQLException {\n");

        builder.append("        System.out.println();\n");
        builder.append("        System.out.println(\"Inserting example model...\");\n");
        builder.append("\n");

        builder.append("        ");
        builder.append(standard.getModelClassName(table));
        builder.append(" model = ");
        builder.append("new ");
        builder.append(standard.getModelClassName(table));
        builder.append("();\n");

        for (Column column : columns) {
            TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);

            if (column.autoIncrement()) {
                continue;
            }

            builder.append("        model.");
            builder.append(standard.getModelSetterName(column));
            builder.append("(");
            builder.append(descriptor.getRandomValue());
            builder.append(");\n");
        }

        builder.append("\n");
        builder.append("        dao.");
        builder.append(standard.getDAOInsertMethodName(table));
        builder.append("(model);\n");

        builder.append("\n");
        builder.append("        System.out.println(\"- \" + model);\n");

        builder.append("\n");
        builder.append("        return model;\n");

        builder.append("    }\n");
    }

    private void appendUpdateExampleMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> nonPrimaryKeyBaseColumns = table.nonPrimaryKeyBaseColumns();

        builder.append("\n");
        builder.append("    public static void ");
        builder.append(standard.getExampleUpdateMethodName(table));
        builder.append("(");
        builder.append(standard.getDAOClassName(table));
        builder.append(" dao, ");
        builder.append(standard.getModelClassName(table));
        builder.append(" model");
        builder.append(") throws java.sql.SQLException {\n");

        builder.append("        System.out.println();\n");
        builder.append("        System.out.println(\"Updating example model...\");\n");
        builder.append("\n");

        for (Column column : nonPrimaryKeyBaseColumns) {
            TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);

            if (column.autoIncrement()) {
                continue;
            }

            builder.append("        model.");
            builder.append(standard.getModelSetterName(column));
            builder.append("(");
            builder.append(descriptor.getRandomValue());
            builder.append(");\n");
        }

        builder.append("\n");
        builder.append("        dao.");
        builder.append(standard.getDAOUpdateMethodName(table));
        builder.append("(model);\n");

        builder.append("\n");
        builder.append("        System.out.println(\"- \" + model);\n");

        builder.append("    }\n");
    }

    private void appendDeleteExampleMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        builder.append("\n");
        builder.append("    public static void ");
        builder.append(standard.getExampleDeleteMethodName(table));
        builder.append("(");
        builder.append(standard.getDAOClassName(table));
        builder.append(" dao, ");
        builder.append(standard.getModelClassName(table));
        builder.append(" model");
        builder.append(") throws java.sql.SQLException {\n");

        builder.append("        System.out.println();\n");
        builder.append("        System.out.println(\"Deleting example model...\");\n");
        builder.append("\n");

        builder.append("        dao.");
        builder.append(standard.getDAODeleteMethodName(table));
        builder.append("(model);\n");

        builder.append("    }\n");
    }

    private void appendGetExampleMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> primaryKeyColumns = table.primaryKeyColumns();

        builder.append("\n");
        builder.append("    public static void ");
        builder.append(standard.getExampleGetMethodName(table));
        builder.append("(");
        builder.append(standard.getDAOClassName(table));
        builder.append(" dao, ");
        builder.append(primaryKeyColumns.stream()
                .map(column -> TypeDescriptor.fromColumn(column).getJavaTypeName() + " "
                        + standard.getModelAttributeName(column))
                .collect(Collectors.joining(", ")));
        builder.append(") throws java.sql.SQLException {\n");

        builder.append("        System.out.println();\n");
        builder.append("        System.out.println(\"Getting example model...\");\n");
        builder.append("\n");

        builder.append("        ");
        builder.append(standard.getModelClassName(table));
        builder.append(" model = dao.");
        builder.append(standard.getDAOGetMethodName(table));
        builder.append("(");
        builder.append(primaryKeyColumns.stream()
                .map(column -> standard.getModelAttributeName(column))
                .collect(Collectors.joining(", ")));
        builder.append(");\n");

        builder.append("\n");
        builder.append("        System.out.println(\"- \" + model);\n");

        builder.append("    }\n");
    }

    private void appendListExampleMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        builder.append("\n");
        builder.append("    public static void ");
        builder.append(standard.getExampleListMethodName(table));
        builder.append("(");
        builder.append(standard.getDAOClassName(table));
        builder.append(" dao) throws java.sql.SQLException {\n");

        builder.append("        System.out.println();\n");
        builder.append("        System.out.println(\"Listing example models...\");\n");
        builder.append("\n");

        builder.append("        java.util.List<");
        builder.append(standard.getModelClassName(table));
        builder.append("> models = dao.");
        builder.append(standard.getDAOListMethodName(table));
        builder.append("();\n");

        builder.append("\n");
        builder.append("        for (");
        builder.append(standard.getModelClassName(table));
        builder.append(" model : models) {\n");
        builder.append("            System.out.println(\"- \" + model);\n");
        builder.append("        }\n");

        builder.append("    }\n");
    }
}
