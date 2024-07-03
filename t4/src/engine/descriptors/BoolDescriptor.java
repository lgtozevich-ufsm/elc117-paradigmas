package engine.descriptors;

public class BoolDescriptor extends TypeDescriptor {
    @Override
    public String getSQLTypeName() {
        return "BOOL";
    }

    @Override
    public String getJavaTypeName() {
        return "boolean";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getBoolean";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setBoolean";
    }

    @Override
    public String getRandomValue() {
        return String.valueOf(Math.random() > 0.5);
    }
}
