package engine.descriptors;

public class FloatDescriptor extends TypeDescriptor {
    @Override
    public String getJavaTypeName() {
        return "float";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getFloat";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setFloat";
    }

    @Override
    public String getRandomValue() {
        return String.valueOf(Math.random() * 100.0f);
    }
}
