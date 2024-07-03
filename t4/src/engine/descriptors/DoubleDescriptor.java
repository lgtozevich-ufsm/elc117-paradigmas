package engine.descriptors;

public class DoubleDescriptor extends TypeDescriptor {
    @Override
    public String getSQLTypeName() {
        return "DOUBLE";
    }

    @Override
    public String getJavaTypeName() {
        return "double";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getDouble";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setDouble";
    }

    @Override
    public String getRandomValue() {
        return String.valueOf(Math.random() * 100.0);
    }
}
