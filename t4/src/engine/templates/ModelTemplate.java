package engine.templates;

import database.Column;
import database.Table;
import engine.Resource;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.TemplateUtils;
import engine.descriptors.TypeDescriptor;

import java.util.List;

public class ModelTemplate implements Template {
    @Override
    public Resource render(TemplateStandard standard, TemplateSettings settings) {
        StringBuilder builder = new StringBuilder();
        
        Table table = settings.table();
        List<Column> columns = table.baseColumns();

        builder.append("public class ");
        builder.append(standard.getModelClassName(table));
        builder.append(" {\n");

        appendColumnAttributes(builder, standard, columns);
        appendConstructor(builder, standard, table);

        for (Column column : columns) {
            appendColumnGetter(builder, standard, column);
            appendColumnSetter(builder, standard, column);
        }

        appendToStringMethod(builder, standard, table);

        builder.append("}\n");

        return new Resource(standard.getModelClassName(table) + ".java", builder.toString());
    }

    private void appendConstructor(StringBuilder builder, TemplateStandard standard, Table table) {
        builder.append("\n");
        builder.append("    public ");
        builder.append(standard.getModelClassName(table));
        builder.append("() { }\n");
    }

    private void appendColumnAttributes(StringBuilder builder, TemplateStandard standard, List<Column> columns) {
        for (Column column : columns) {
            TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);

            builder.append("    private ");
            builder.append(descriptor.getJavaTypeName());
            builder.append(" ");
            builder.append(standard.getModelAttributeName(column));
            builder.append(";\n");
        }
    }

    private void appendColumnGetter(StringBuilder builder, TemplateStandard standard, Column column) {
        TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);

        builder.append("\n");
        builder.append("    public ");
        builder.append(descriptor.getJavaTypeName());
        builder.append(" ");
        builder.append(standard.getModelGetterName(column));
        builder.append("() {\n");

        builder.append("        return ");
        builder.append(standard.getModelAttributeName(column));
        builder.append(";\n");

        builder.append("    }\n");
    }

    private void appendColumnSetter(StringBuilder builder, TemplateStandard standard, Column column) {
        TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);

        builder.append("\n");
        builder.append("    public void ");
        builder.append(standard.getModelSetterName(column));
        builder.append("(");
        builder.append(descriptor.getJavaTypeName());
        builder.append(" ");
        builder.append(standard.getModelAttributeName(column));
        builder.append(") {\n");

        builder.append("        this.");
        builder.append(standard.getModelAttributeName(column));
        builder.append(" = ");
        builder.append(standard.getModelAttributeName(column));
        builder.append(";\n");

        builder.append("    }\n");
    }

    private void appendToStringMethod(StringBuilder builder, TemplateStandard standard, Table table) {
        List<Column> columns = table.baseColumns();

        builder.append("\n");
        builder.append("    @Override\n");
        builder.append("    public String toString() {\n");

        builder.append("        return \"");
        builder.append(standard.getModelClassName(table));
        builder.append(" {\"");

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);

            if (i > 0) {
                builder.append(" + \", \"");
            }

            builder.append(" + ");
            builder.append(TemplateUtils.escapeString(standard.getModelAttributeName(column)));
            builder.append(" + \"=\" + ");
            builder.append(standard.getModelGetterName(column));
            builder.append("()");
        }

        builder.append(" + \"}\";\n");
        builder.append("    }\n");
    }
}
