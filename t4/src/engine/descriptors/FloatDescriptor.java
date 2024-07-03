package engine.descriptors;

import java.util.Random;

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
        return Math.floor(new Random().nextFloat(1.0f, 1000.0f)) / 100.0f + "f";
    }
}
