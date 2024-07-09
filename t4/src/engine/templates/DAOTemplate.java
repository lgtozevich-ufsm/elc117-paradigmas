package engine.templates;

import java.util.List;
import java.util.stream.Collectors;

import database.Column;
import database.Table;
import engine.Resource;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.TemplateUtils;
import engine.descriptors.TypeDescriptor;

public class DAOTemplate implements Template {
    @Override
    public Resource render(TemplateStandard standard, TemplateSettings settings) {
        StringBuilder builder = new StringBuilder();

        Table table = settings.table();

        if (table.primaryKeyColumns().isEmpty()) {
            throw new UnsupportedOperationException("No primary key available to uniquely identify rows.");
        }

        builder.append("public class ");
        builder.append(standard.getDAOClassName(table));
        builder.append(" {\n");

        builder.append("    private final java.sql.Connection connection;\n");

        appendConstructor(builder, standard, table);
        appendInsertMethod(builder, standard, table);
        appendUpdateMethod(builder, standard, table);
        appendDeleteMethod(builder, standard, table);
        appendGetMethod(builder, standard, table);
        appendListMethod(builder, standard, table);
        appendMapMethod(builder, standard, table);

        builder.append("}\n");

        return new Resource(standard.getDAOClassName(table) + ".java", builder.toString());
    }

    private void appendConstructor(StringBuilder builder, TemplateStandard standard, Table table) {
        builder.append("\n");

        builder.append("    public ");
        builder.append(standard.getDAOClassName(table));
        builder.append("(java.sql.Connection connection) {\n");

        builder.append("        this.connection = connection;\n");
        builder.append("    }\n");
    }

    private void appendInsertMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> baseColumns = table.baseColumns();
        List<Column> autoIncrementedColumns = table.autoIncrementedColumns();

        if (autoIncrementedColumns.size() > 1) {
            throw new UnsupportedOperationException("Multiple auto-incremented columns detected");
        }

        builder.append("\n");
        builder.append("    public void ");
        builder.append(standard.getDAOInsertMethodName(table));
        builder.append("(");
        builder.append(standard.getModelClassName(table));
        builder.append(" model) throws java.sql.SQLException {\n");

        builder.append("        String sql = \"INSERT INTO ");
        builder.append(standard.getTableQualifiedName(table));
        builder.append("(");
        appendColumnQueryNames(builder, standard, baseColumns);
        builder.append(") VALUES (");
        appendColumnQueryPlaceholders(builder, baseColumns);
        builder.append(")\";\n");

        builder.append("\n");
        builder.append(
                "        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {\n");

        for (int i = 0; i < baseColumns.size(); i++) {
            Column column = baseColumns.get(i);
            TypeDescriptor columnDescriptor = TypeDescriptor.fromColumn(column);

            builder.append("            statement.");
            builder.append(columnDescriptor.getJdbcSetterMethodName());
            builder.append("(");
            builder.append(i + 1);
            builder.append(", ");
            builder.append("model.");
            builder.append(standard.getModelGetterName(column));
            builder.append("());\n");
        }

        builder.append("\n");
        builder.append("            statement.executeUpdate();\n");

        if (!autoIncrementedColumns.isEmpty()) {
            Column autoIncrementedColumn = autoIncrementedColumns.getFirst();
            TypeDescriptor autoIncrementedColumnDescriptor = TypeDescriptor.fromColumn(autoIncrementedColumn);

            builder.append("\n");
            builder.append("            try (java.sql.ResultSet generatedKeys = statement.getGeneratedKeys()) {\n");
            builder.append("                if (generatedKeys.next()) {\n");

            builder.append("                    model.");
            builder.append(standard.getModelSetterName(autoIncrementedColumn));
            builder.append("(generatedKeys.");
            builder.append(autoIncrementedColumnDescriptor.getJdbcGetterMethodName());
            builder.append("(1));\n");

            builder.append("                }\n");
            builder.append("            }\n");
        }

        builder.append("        }\n");
        builder.append("    }\n");
    }

    private void appendGetMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> primaryKeyColumns = table.primaryKeyColumns();

        builder.append("\n");
        builder.append("    public ");
        builder.append(standard.getModelClassName(table));
        builder.append(" ");
        builder.append(standard.getDAOGetMethodName(table));
        builder.append("(");
        appendColumnMethodParameters(builder, standard, primaryKeyColumns);
        builder.append(") throws java.sql.SQLException {\n");

        builder.append("        String sql = \"");
        builder.append("SELECT * FROM ");
        builder.append(standard.getTableQualifiedName(table));
        builder.append(" WHERE ");
        appendColumnQueryConditions(builder, standard, primaryKeyColumns);
        builder.append("\";\n");

        builder.append("\n");
        builder.append("        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {\n");

        for (int i = 0; i < primaryKeyColumns.size(); i++) {
            Column column = primaryKeyColumns.get(i);
            TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);

            builder.append("            statement.");
            builder.append(descriptor.getJdbcSetterMethodName());
            builder.append("(");
            builder.append(i + 1);
            builder.append(", ");
            builder.append(standard.getModelAttributeName(column));
            builder.append(");\n");
        }

        builder.append("\n");
        builder.append("            java.sql.ResultSet resultSet = statement.executeQuery();\n");
        builder.append("\n");
        builder.append("            if (resultSet.next()) {\n");

        builder.append("                return ");
        builder.append(standard.getDAOMapMethodName(table));
        builder.append("(resultSet);\n");

        builder.append("            }\n");
        builder.append("        }\n");
        builder.append("\n");
        builder.append("        return null;\n");
        builder.append("    }\n");
    }

    private void appendUpdateMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> primaryKeyColumns = table.primaryKeyColumns();
        List<Column> nonPrimaryKeyColumns =  table.nonPrimaryKeyBaseColumns();

        builder.append("\n");
        builder.append("    public void ");
        builder.append(standard.getDAOUpdateMethodName(table));
        builder.append("(");
        builder.append(standard.getModelClassName(table));
        builder.append(" model) throws java.sql.SQLException {\n");

        builder.append("        String sql = \"");
        builder.append("UPDATE ");
        builder.append(standard.getTableQualifiedName(table));
        builder.append(" SET ");
        appendColumnQueryAssignments(builder, standard, nonPrimaryKeyColumns);
        builder.append(" WHERE ");
        appendColumnQueryConditions(builder, standard, primaryKeyColumns);
        builder.append("\";\n");

        builder.append("\n");
        builder.append("        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {\n");

        for (int i = 0; i < nonPrimaryKeyColumns.size(); i++) {
            Column column = nonPrimaryKeyColumns.get(i);
            TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);

            builder.append("            statement.");
            builder.append(descriptor.getJdbcSetterMethodName());
            builder.append("(");
            builder.append(i + 1);
            builder.append(", model.");
            builder.append(standard.getModelGetterName(column));
            builder.append("());\n");
        }

        for (int i = 0; i < primaryKeyColumns.size(); i++) {
            Column column = primaryKeyColumns.get(i);
            TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);

            builder.append("            statement.");
            builder.append(descriptor.getJdbcSetterMethodName());
            builder.append("(");
            builder.append(nonPrimaryKeyColumns.size() + i + 1);
            builder.append(", model.");
            builder.append(standard.getModelGetterName(column));
            builder.append("());\n");
        }

        builder.append("\n");
        builder.append("            statement.executeUpdate();\n");
        builder.append("        }\n");
        builder.append("    }\n");
    }

    private void appendDeleteMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> primaryKeyColumns = table.primaryKeyColumns();

        builder.append("\n");
        builder.append("    public void ");
        builder.append(standard.getDAODeleteMethodName(table));
        builder.append("(");
        builder.append(standard.getModelClassName(table));
        builder.append(" model) throws java.sql.SQLException {\n");

        builder.append("        String sql = \"");
        builder.append("DELETE FROM ");
        builder.append(standard.getTableQualifiedName(table));
        builder.append(" WHERE ");
        appendColumnQueryConditions(builder, standard, primaryKeyColumns);
        builder.append("\";\n");

        builder.append("\n");
        builder.append("        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {\n");

        for (int i = 0; i < primaryKeyColumns.size(); i++) {
            Column column = primaryKeyColumns.get(i);
            TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);

            builder.append("            statement.");
            builder.append(descriptor.getJdbcSetterMethodName());
            builder.append("(");
            builder.append(i + 1);
            builder.append(", model.");
            builder.append(standard.getModelGetterName(column));
            builder.append("());\n");
        }

        builder.append("\n");
        builder.append("            statement.executeUpdate();\n");
        builder.append("        }\n");
        builder.append("    }\n");
    }

    private void appendListMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        builder.append("\n");
        builder.append("    public ");
        builder.append("java.util.List<");
        builder.append(standard.getModelClassName(table));
        builder.append("> ");
        builder.append(standard.getDAOListMethodName(table));
        builder.append("() throws java.sql.SQLException {\n");

        builder.append("        java.util.List<");
        builder.append(standard.getModelClassName(table));
        builder.append("> models = new java.util.ArrayList<>();\n");

        builder.append("        String sql = \"");
        builder.append("SELECT * FROM ");
        builder.append(standard.getTableQualifiedName(table));
        builder.append("\";\n");

        builder.append("\n");
        builder.append("        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {\n");
        builder.append("            java.sql.ResultSet resultSet = statement.executeQuery();\n");
        builder.append("\n");
        builder.append("            while (resultSet.next()) {\n");

        builder.append("                models.add(");
        builder.append(standard.getDAOMapMethodName(table));
        builder.append("(resultSet));\n");

        builder.append("            }\n");
        builder.append("        }\n");
        builder.append("\n");
        builder.append("        return models;\n");
        builder.append("    }\n");
    }

    private void appendMapMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> baseColumns = table.baseColumns();

        builder.append("\n");
        builder.append("    private ");
        builder.append(standard.getModelClassName(table));
        builder.append(" ");
        builder.append(standard.getDAOMapMethodName(table));
        builder.append("(java.sql.ResultSet resultSet) throws java.sql.SQLException {\n");

        builder.append("        ");
        builder.append(standard.getModelClassName(table));
        builder.append(" model = new ");
        builder.append(standard.getModelClassName(table));
        builder.append("();\n");

        for (Column column : baseColumns) {
            TypeDescriptor columnDescriptor = TypeDescriptor.fromColumn(column);

            builder.append("        model.");
            builder.append(standard.getModelSetterName(column));
            builder.append("(resultSet.");
            builder.append(columnDescriptor.getJdbcGetterMethodName());
            builder.append("(");
            builder.append(TemplateUtils.escapeString(column.name()));
            builder.append("));\n");
        }

        builder.append("\n");
        builder.append("        return model;\n");
        builder.append("    }\n");
    }

    private void appendColumnQueryNames(StringBuilder builder, TemplateStandard standard, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(standard::getColumnQualifiedName)
                .collect(Collectors.joining(", ")));
    }

    private void appendColumnQueryPlaceholders(StringBuilder builder, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(c -> "?")
                .collect(Collectors.joining(", ")));
    }

    private void appendColumnQueryConditions(StringBuilder builder, TemplateStandard standard, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(column -> standard.getColumnQualifiedName(column) + " = ?")
                .collect(Collectors.joining(" AND ")));
    }

    private void appendColumnQueryAssignments(StringBuilder builder, TemplateStandard standard, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(column -> standard.getColumnQualifiedName(column) + " = ?")
                .collect(Collectors.joining(", ")));
    }

    private void appendColumnMethodParameters(StringBuilder builder, TemplateStandard standard, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(c -> TypeDescriptor.fromColumn(c).getJavaTypeName() + " " + standard.getModelAttributeName(c))
                .collect(Collectors.joining(", ")));
    }
}
