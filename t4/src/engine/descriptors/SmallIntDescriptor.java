package engine.descriptors;

public class SmallIntDescriptor extends TypeDescriptor {

    public String getSQLTypeName() {
        return "SMALLINT";
    }

    public String getJavaTypeName() {
        return "short";
    }

    public String getJdbcGetterMethodName() {
        return "getShort";
    }

    public String getJdbcSetterMethodName() {
        return "setShort";
    }

    public String getRandomValue() {
        return String.valueOf((short) (Math.random() * 100));
    }
}