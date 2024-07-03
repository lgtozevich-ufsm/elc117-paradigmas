package engine.descriptors;

public class IntegerDescriptor extends TypeDescriptor {
    @Override
    public String getSQLTypeName() {
        return "INT";
    }

    @Override
    public String getJavaTypeName() {
        return "int";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getInt";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setInt";
    }

    @Override
    public String getRandomValue() {
        return "123";
    }
}
