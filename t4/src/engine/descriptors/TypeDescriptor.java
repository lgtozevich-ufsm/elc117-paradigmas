package engine.descriptors;

import database.Column;

public abstract class TypeDescriptor {
    public abstract String getJavaTypeName();

    public abstract String getJdbcGetterMethodName();

    public abstract String getJdbcSetterMethodName();

    public abstract String getRandomValue();

    public static TypeDescriptor fromColumn(Column column) {
        switch (column.typeName()) {
            case "INT":
                return new IntegerDescriptor();
            case "BIT":
                return new BooleanDescriptor();
            case "CHAR":
            case "VARCHAR":
            case "BINARY":
            case "VARBINARY":
            case "TINYBLOB":
            case "TINYTEXT":
            case "TEXT":
            case "BLOB":
            case "MEDIUMTEXT":
            case "MEDIUMBLOB":
            case "LONGTEXT":
            case "LONGBLOB":
                return new StringDescriptor(column.size());
            case "DATE":
                return new DateDescriptor();
            case "TIME":
                return new TimeDescriptor();
            case "DATETIME":
            case "TIMESTAMP":
                return new TimestampDescriptor();
            case "FLOAT":
                return new FloatDescriptor();
            case "DOUBLE":
                return new DoubleDescriptor();
            case "DECIMAL":
                return new DecimalDescriptor(column.size(), column.decimalDigits());
            case "BIGINT":
                return new LongDescriptor();
            case "SMALLINT":
                return new ShortDescriptor();
            default:
                return null;
        }
    }
}
