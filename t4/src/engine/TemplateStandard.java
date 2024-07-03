package engine;

import database.Column;
import database.Table;

public class TemplateStandard {
    public String getModelClassName(Table table) {
        return TemplateUtils.convertCamelCaseToPascalCase(table.name());
    }

    public String getDAOClassName(Table table) {
        return getModelClassName(table) + "DAO";
    }

    public String getModelAttributeName(Column column) {
        return column.name();
    }

    public String getModelGetterName(Column column) {
        return "get" + TemplateUtils.convertCamelCaseToPascalCase(column.name());
    }

    public String getModelSetterName(Column column) {
        return "set" + TemplateUtils.convertCamelCaseToPascalCase(column.name());
    }

    public String getDAOInsertMethodName(Table table) {
        return "insert";
    }

    public String getDAOUpdateMethodName(Table table) {
        return "update";
    }

    public String getDAODeleteMethodName(Table table) {
        return "delete";
    }

    public String getDAOGetMethodName(Table table) {
        return "get";
    }

    public String getDAOListMethodName(Table table) {
        return "list";
    }

    public String getDAOMapMethodName(Table table) {
        return "map";
    }

    public String getExampleClassName(Table table) {
        return getModelClassName(table) + "Example";
    }

    public String getExampleInsertMethodName(Table table) {
        return "insertExample";
    }

    public String getExampleUpdateMethodName(Table table) {
        return "updateExample";
    }

    public String getExampleDeleteMethodName(Table table) {
        return "deleteExample";
    }

    public String getExampleGetMethodName(Table table) {
        return "getExample";
    }

    public String getExampleListMethodName(Table table) {
        return "listExample";
    }

    public String getTableQualifiedName(Table table) {
        return TemplateUtils.escapeIdentifier(table.catalog()) + "." + TemplateUtils.escapeIdentifier(table.name());
    }

    public String getColumnQualifiedName(Column column) {
        return TemplateUtils.escapeIdentifier(column.tableCatalog()) + "."
                + TemplateUtils.escapeIdentifier(column.tableName()) + "."
                + TemplateUtils.escapeIdentifier(column.name());
    }
}
