package engine.templates;

import database.Column;
import engine.Template;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.TypeSpecification;

public class ModelTemplate implements Template {
    @Override
    public String render(TemplateStandard standard, TemplateSettings settings) {
        StringBuilder builder = new StringBuilder();
        var table = settings.table();

        builder.append("public class ").append(standard.getModelTypeName(table)).append(" {\n");

        for (Column column : table.columns()) {
            TypeSpecification type = TypeSpecification.fromDatabaseTypeName(column.typeName());
            builder.append("    private ").append(type.getJavaTypeName()).append(" ").append(column.name()).append(";\n");
        }

        for (Column column : table.columns()) {
            appendGetter(builder, standard, column);
            appendSetter(builder, standard, column);
        }

        builder.append("}\n");

        return builder.toString();
    }

    private void appendGetter(StringBuilder builder, TemplateStandard standard, Column column) {
        TypeSpecification type = TypeSpecification.fromDatabaseTypeName(column.typeName());
        builder.append("    public ").append(type.getJavaTypeName()).append(" ").append(standard.getModelGetterName(column)).append("() {\n");
        builder.append("        return ").append(column.name()).append(";\n");
        builder.append("    }\n");
    }

    private void appendSetter(StringBuilder builder, TemplateStandard standard, Column column) {
        TypeSpecification type = TypeSpecification.fromDatabaseTypeName(column.typeName());
        builder.append("    public void ").append(standard.getModelSetterName(column)).append("(").append(type.getJavaTypeName()).append(" ").append(column.name()).append(") {\n");
        builder.append("        this.").append(column.name()).append(" = ").append(column.name()).append(";\n");
        builder.append("    }\n");
    }
}
