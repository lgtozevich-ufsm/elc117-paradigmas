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
            throw new UnsupportedOperationException("No primary key columns detected");
        }

        builder.append("class ");
        builder.append(standard.getDAOClassName(table));
        builder.append(" {\n");

        builder.append("    private java.sql.Connection connection;\n");

        appendConstructor(builder, standard, table);
        appendInsertMethod(builder, standard, table);
        appendUpdateMethod(builder, standard, table);
        appendDeleteMethod(builder, standard, table);
        appendGetMethod(builder, standard, table);
        appendListMethod(builder, standard, table);
        appendMapMethod(builder, standard, table);

        builder.append("}\n");

        return new Resource(standard.getDAOClassName(table), builder.toString());
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
        builder.append(TemplateUtils.escapeIdentifier(table.name()));
        builder.append("(");
        appendColumnQueryNames(builder, baseColumns);
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
        builder.append(TemplateUtils.escapeIdentifier(table.name()));
        builder.append(" WHERE ");
        appendColumnQueryConditions(builder, primaryKeyColumns);
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
        List<Column> baseColumns = table.baseColumns();
        List<Column> primaryKeyColumns = table.primaryKeyColumns();
        
        List<Column> updatableColumns = baseColumns.stream().filter(c -> !primaryKeyColumns.contains(c)).collect(Collectors.toList());

        builder.append("\n");
        builder.append("    public void ");
        builder.append(standard.getDAOUpdateMethodName(table));
        builder.append("(");
        builder.append(standard.getModelClassName(table));
        builder.append(" model) throws java.sql.SQLException {\n");

        builder.append("        String sql = \"");
        builder.append("UPDATE ");
        builder.append(TemplateUtils.escapeIdentifier(table.name()));
        builder.append(" SET ");
        appendColumnQueryAssignments(builder, updatableColumns);
        builder.append(" WHERE ");
        appendColumnQueryConditions(builder, primaryKeyColumns);
        builder.append("\";\n");

        builder.append("\n");
        builder.append("        try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {\n");

        for (int i = 0; i < updatableColumns.size(); i++) {
            Column column = updatableColumns.get(i);
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
            builder.append(updatableColumns.size() + i + 1);
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

        if (primaryKeyColumns.size() == 0) {
            throw new UnsupportedOperationException("No primary key columns detected");
        }

        builder.append("\n");
        builder.append("    public void ");
        builder.append(standard.getDAODeleteMethodName(table));
        builder.append("(");
        appendColumnMethodParameters(builder, standard, primaryKeyColumns);
        builder.append(") throws java.sql.SQLException {\n");

        builder.append("        String sql = \"");
        builder.append("DELETE FROM ");
        builder.append(TemplateUtils.escapeIdentifier(table.name()));
        builder.append(" WHERE ");
        appendColumnQueryConditions(builder, primaryKeyColumns);
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
        builder.append(TemplateUtils.escapeIdentifier(table.name()));
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

    private void appendColumnQueryNames(StringBuilder builder, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(Column::name)
                .map(TemplateUtils::escapeIdentifier)
                .collect(Collectors.joining(", ")));
    }

    private void appendColumnQueryPlaceholders(StringBuilder builder, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(c -> "?")
                .collect(Collectors.joining(", ")));
    }

    private void appendColumnQueryConditions(StringBuilder builder, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(column -> TemplateUtils.escapeIdentifier(column.name()) + " = ?")
                .collect(Collectors.joining(" AND ")));
    }

    private void appendColumnQueryAssignments(StringBuilder builder, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(column -> TemplateUtils.escapeIdentifier(column.name()) + " = ?")
                .collect(Collectors.joining(", ")));
    }

    private void appendColumnMethodParameters(StringBuilder builder, TemplateStandard standard, List<Column> columns) {
        builder.append(columns
                .stream()
                .map(c -> TypeDescriptor.fromColumn(c).getJavaTypeName() + " " + standard.getModelAttributeName(c))
                .collect(Collectors.joining(", ")));
    }
}
