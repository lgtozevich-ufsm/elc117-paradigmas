package engine.descriptors;

public class BooleanDescriptor extends TypeDescriptor {
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
        return Math.random() > 0.5 ? "true" : "false";
    }
}
