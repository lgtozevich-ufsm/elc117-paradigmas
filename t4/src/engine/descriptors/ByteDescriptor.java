package engine.descriptors;

import java.util.Random;

public class ByteDescriptor extends TypeDescriptor {
    @Override
    public String getJavaTypeName() {
        return "byte";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getByte";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setByte";
    }

    @Override
    public String getRandomValue() {
        return "(byte)" + String.valueOf(new Random().nextInt(Byte.MAX_VALUE + 1));
    }
}
