package engine.descriptors;

import database.Column;

public abstract class TypeDescriptor {
    public abstract String getJavaTypeName();

    public abstract String getJdbcGetterMethodName();

    public abstract String getJdbcSetterMethodName();

    public abstract String getRandomValue();

    public static TypeDescriptor fromColumn(Column column) {
        switch (column.typeName()) {
            case "BIT":
                return new BitDescriptor(column.size());
            case "TINYINT":
                return new ByteDescriptor();
            case "SMALLINT":
                return new ShortDescriptor();
            case "MEDIUMINT":
            case "INT":
                return new IntegerDescriptor();
            case "BIGINT":
                return new LongDescriptor();
            case "FLOAT":
                return new FloatDescriptor();
            case "DOUBLE":
                return new DoubleDescriptor();
            case "DECIMAL":
                return new DecimalDescriptor(column.size(), column.decimalDigits());
            case "DATE":
                return new DateDescriptor();
            case "TIME":
                return new TimeDescriptor();
            case "DATETIME":
            case "TIMESTAMP":
                return new TimestampDescriptor();
            case "CHAR":
            case "VARCHAR":
            case "TINYTEXT":
            case "MEDIUMTEXT":
            case "LONGTEXT":
            case "TEXT":
                return new StringDescriptor(column.size());
            default:
                throw new IllegalArgumentException("Unknown column type: " + column.typeName());
        }
    }
}
