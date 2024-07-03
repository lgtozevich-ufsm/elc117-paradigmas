package engine;

import database.Column;
import database.Table;

public class TemplateStandard {
    public String getModelTypeName(Table table) {
        return table.name();
    }

    public String getDAOTypeName(Table table) {
        return table.name() + "DAO";
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

    public String getModelGetterName(Column column) {
        return "get" + convertCamelCaseToPascalCase(column.name());
    }

    public String getModelSetterName(Column column) {
        return "set" + convertCamelCaseToPascalCase(column.name());
    }

    private String convertCamelCaseToPascalCase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
