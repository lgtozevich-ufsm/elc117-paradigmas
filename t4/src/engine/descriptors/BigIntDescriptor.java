package engine.descriptors;

public class BigIntDescriptor extends TypeDescriptor {

    public String getSQLTypeName() {
        return "BIGINT";
    }

    public String getJavaTypeName() {
        return "Long";
    }

    public String getJdbcGetterMethodName() {
        return "getLong";
    }

    public String getJdbcSetterMethodName() {
        return "setLong";
    }

    public String getRandomValue() {
        return "new Long(" + (long) (Math.random() * Long.MAX_VALUE) + "L)";
    }
}
