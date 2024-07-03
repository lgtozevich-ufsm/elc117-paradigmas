package engine.templates;

import java.util.stream.Collectors;

import database.Column;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.TemplateUtils;
import engine.TypeSpecification;

public class DAOTemplate implements Template {
    @Override
    public String render(TemplateStandard standard, TemplateSettings settings) {
        StringBuilder builder = new StringBuilder();

        builder.append("class " + standard.getDAOTypeName(settings.table()) + " {\n");
        builder.append("    private Connection connection;\n");

        appendConstructor(builder, standard, settings);
        appendInsertMethod(builder, standard, settings);

        builder.append("}\n");

        return builder.toString();
    }

    private void appendConstructor(StringBuilder builder, TemplateStandard standard, TemplateSettings settings) {
        builder.append("\n");
        builder.append("    public " + standard.getModelTypeName(settings.table()) + "DAO(Connection connection) {\n");
        builder.append("        this.connection = connection;\n");
        builder.append("    }\n");
    }

    private void appendInsertMethod(StringBuilder builder, TemplateStandard standard, TemplateSettings settings) {
        builder.append("\n");
        builder.append("   public void insert(" + standard.getModelTypeName(settings.table()));
        builder.append(" model) throws SQLException {\n");

        builder.append("        String sql = \"INSERT INTO " + settings.table() + "(");
        builder.append(settings
                .table()
                .columns()
                .stream()
                .map(c -> TemplateUtils.escapeColumnName(c.name()))
                .collect(Collectors.joining(", ")));
        builder.append(") VALUES (");
        builder.append(settings
                .table()
                .columns()
                .stream()
                .map(c -> "?")
                .collect(Collectors.joining(", ")));
        builder.append(")\";\n");

        builder.append("        try (PreparedStatement statement = connection.prepareStatement(sql)) {\n");

        for (int i = 0; i < settings.table().columns().size(); i++) {
            Column column = settings.table().columns().get(i);
            TypeSpecification type = TypeSpecification.fromDatabaseTypeName(column.typeName());

            builder.append("            statement.set" + type.getJdbcSetterMethodName() + "(" + (i + 1) + ", ");
            builder.append("model." + standard.getModelGetterName(column) + "());\n");
        }
    }
}
