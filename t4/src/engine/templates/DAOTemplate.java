package engine.templates;

import java.util.List;
import java.util.stream.Collectors;

import database.Column;
import database.Table;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.TemplateUtils;

public class DAOTemplate implements Template {
    @Override
    public String render(TemplateStandard standard, TemplateSettings settings) {
        StringBuilder builder = new StringBuilder();

        builder.append("class ")
                .append(standard.getDAOTypeName(settings.table()))
                .append(" {\n");

        builder.append("    private Connection connection;\n");

        appendConstructor(builder, standard, settings.table());
        appendInsertMethod(builder, standard, settings.table());
        appendUpdateMethod(builder, standard, settings.table());
        appendDeleteMethod(builder, standard, settings.table());
        appendGetMethod(builder, standard, settings.table());
        appendListMethod(builder, standard, settings.table());
        appendMapMethod(builder, standard, settings.table());

        builder.append("}\n");

        return builder.toString();
    }

    private void appendConstructor(StringBuilder builder, TemplateStandard standard, Table table) {
        builder.append("\n");

        builder.append("    public ")
                .append(standard.getModelTypeName(table))
                .append("DAO(Connection connection) {\n");

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

        builder.append("   public void")
                .append(standard.getDAOInsertMethodName(table))
                .append("(")
                .append(standard.getModelTypeName(table))
                .append(" model) throws SQLException {\n");

        builder.append("        String sql = \"INSERT INTO ")
                .append(table.name())
                .append(")")
                .append(baseColumns
                        .stream()
                        .map(Column::name)
                        .map(TemplateUtils::escapeColumnName)
                        .collect(Collectors.joining(", ")))
                .append(") VALUES (")
                .append(baseColumns
                        .stream()
                        .map(_ -> "?")
                        .collect(Collectors.joining(", ")))
                .append(")\";\n");

        builder.append("\n");
        builder.append("        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {\n");

        for (int i = 0; i < baseColumns.size(); i++) {
            Column column = baseColumns.get(i);
            TypeSpecification type = TypeSpecification.fromDatabaseTypeName(column.typeName());

            builder.append("            statement.")
                    .append(type.getJdbcSetterMethodName())
                    .append("(")
                    .append(i + 1)
                    .append(", ");

            builder.append("model.")
                    .append(standard.getModelGetterName(column))
                    .append("());\n");
        }

        builder.append("            statement.executeUpdate();\n");
        builder.append("\n");

        if (!autoIncrementedColumns.isEmpty()) {
            Column autoIncrementedColumn = autoIncrementedColumns.getFirst();

            builder.append("            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {\n");
            builder.append("                if (generatedKeys.next()) {\n");

            builder.append("                        model.")
                    .append(standard.getModelSetterName(autoIncrementedColumn))
                    .append("(generatedKeys.")
                    .append(TypeSpecification.fromDatabaseTypeName(autoIncrementedColumn.typeName()).getJdbcGetterMethodName())
                    .append("(1));\n");
                    
            builder.append("                }\n");
            builder.append("            }\n");
        }

        builder.append("        }\n");
        builder.append("    }\n");
    }

    private void appendListMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        builder.append("\n");

        builder.append("    public ")
                .append("java.util.List<")
                .append(table.name())
                .append("> ")
                .append(standard.getDAOListMethodName(table))
                .append("() throws SQLException {\n");

        builder.append("List<")
                .append(table.name())
                .append("> models = new java.util.ArrayList<>();\n");

        builder.append("        String sql = \"")
                .append("SELECT * FROM ")
                .append(table.name());

        builder.append("\n");
        builder.append("        try (PreparedStatement statement = connection.prepareStatement(sql)) {\n");
        builder.append("            ResultSet resultSet = statement.executeQuery();\n");
        builder.append("            while (resultSet.next()) {\n");

        builder.append("                models.add(")
                .append(standard.getDAOMapMethodName(table))
                .append("(resultSet);\n");

        builder.append("            }\n");
        builder.append("        }\n");
        builder.append("\n");
        builder.append("        return models;\n");
        builder.append("    }\n");
    }



    private void appendMapMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> baseColumns = table.baseColumns();

        builder.append("\n");

        builder.append("    private ")
                .append(standard.getModelTypeName(table))
                .append(" ")
                .append(standard.getDAOMapMethodName(table))
                .append("(ResultSet resultSet) throws SQLException {\n");

        builder.append("        ")
                .append(standard.getModelTypeName(table))
                .append(" model = new ")
                .append(standard.getModelTypeName(table))
                .append("();\n");

        for (Column column : baseColumns) {
            TypeSpecification type = TypeSpecification.fromDatabaseTypeName(column.typeName());

            builder.append("        model.")
                    .append(standard.getModelSetterName(column))
                    .append("(resultSet.")
                    .append(type.getJdbcGetterMethodName())
                    .append("(\"")
                    .append(TemplateUtils.escapeColumnName(column.name()))
                    .append("\"));\n");
        }

        builder.append("        return model;\n");
        builder.append("    }\n");
    }
}
