package engine.descriptors;

import database.Column;

public abstract class TypeDescriptor {
    public abstract String getSQLTypeName();
    public abstract String getJavaTypeName();
    public abstract String getJdbcGetterMethodName();
    public abstract String getJdbcSetterMethodName();
    public abstract String getRandomValue();

    public static TypeDescriptor fromDatabaseTypeName(Column column) {
        switch (column.typeName())
        {
            case "INT":
                return new IntegerDescriptor();
            case "CHAR":
                return new CharDescriptor(column.size());
            default:
                return null;
        }
    }
}
