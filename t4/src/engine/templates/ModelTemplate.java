package engine.templates;

import database.Column;
import engine.Resource;
import engine.TemplateUtils;
import engine.TemplateSettings;
import engine.TemplateStandard;
import engine.descriptors.TypeDescriptor;

public class ModelTemplate implements Template {
    @Override
    public Resource render(TemplateStandard standard, TemplateSettings settings) {
        StringBuilder builder = new StringBuilder();
        var table = settings.table();

        builder.append("public class ").append(standard.getModelClassName(table)).append(" {\n");

        for (Column column : table.columns()) {
            TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);
            builder.append("    private ").append(descriptor.getJavaTypeName()).append(" ").append(column.name()).append(";\n");
        }

        for (Column column : table.columns()) {
            TypeDescriptor descriptor = TypeDescriptor.fromColumn(column);
            appendGetter(builder, standard, column, descriptor);
            appendSetter(builder, standard, column, descriptor);
        }

        builder.append("}\n");

        return new Resource(standard.getModelClassName(table), builder.toString());
    }

    private void appendGetter(StringBuilder builder, TemplateStandard standard, Column column, TypeDescriptor descriptor) {
        builder.append("    public ").append(descriptor.getJavaTypeName()).append(" ").append(standard.getModelGetterName(column)).append("() {\n");
        builder.append("        return ").append(column.name()).append(";\n");
        builder.append("    }\n");
    }

    private void appendSetter(StringBuilder builder, TemplateStandard standard, Column column, TypeDescriptor descriptor) {
        builder.append("    public void ").append(standard.getModelSetterName(column)).append("(").append(descriptor.getJavaTypeName()).append(" ").append(column.name()).append(") {\n");
        builder.append("        this.").append(column.name()).append(" = ").append(column.name()).append(";\n");
        builder.append("    }\n");
    }
}
