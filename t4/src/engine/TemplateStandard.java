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
        return "get" + convertCamelCaseToPascalCase(column.name());
    }

    public String getModelSetterName(Column column) {
        return "set" + convertCamelCaseToPascalCase(column.name());
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

    private String convertCamelCaseToPascalCase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
