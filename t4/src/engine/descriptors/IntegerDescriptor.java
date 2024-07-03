package engine.descriptors;

import java.util.Random;

public class IntegerDescriptor extends TypeDescriptor {
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
        return String.valueOf(new Random().nextInt(1, 100));
    }
}
