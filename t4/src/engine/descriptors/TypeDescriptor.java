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
            case "BOOL":
                return new BoolDescriptor();
            case "VARCHAR":
                return new VarcharDescriptor(column.size());
            case "TEXT":
                return new TextDescriptor();
            case "LONGTEXT":
                return new LongTextDescriptor();
            case "DATE":
                return new DateDescriptor();
            case "TIME":
                return new TimeDescriptor();
            case "DATETIME":
                return new DateTimeDescriptor();
            case "TIMESTAMP":
                return new TimestampDescriptor();
            case "FLOAT":
                return new FloatDescriptor();
            case "DOUBLE":
                return new DoubleDescriptor();
            case "DECIMAL":
                return new DecimalDescriptor(column.size());          
            default:
                return null;
        }
    }
}
