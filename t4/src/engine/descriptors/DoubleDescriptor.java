package engine.descriptors;

import java.util.Random;

public class DoubleDescriptor extends TypeDescriptor {
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
        return String.valueOf(Math.floor(new Random().nextFloat(1.0f, 1000.0f)) / 100.0f);
    }
}
