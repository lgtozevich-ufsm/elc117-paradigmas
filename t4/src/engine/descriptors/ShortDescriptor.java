package engine.descriptors;

import java.util.Random;

public class ShortDescriptor extends TypeDescriptor {
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
        return "(short)" + String.valueOf(new Random().nextInt(1, 100));
    }
}