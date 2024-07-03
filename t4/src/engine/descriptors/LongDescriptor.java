package engine.descriptors;

import java.util.Random;

public class LongDescriptor extends TypeDescriptor {

    public String getSQLTypeName() {
        return "BIGINT";
    }

    public String getJavaTypeName() {
        return "Long";
    }

    public String getJdbcGetterMethodName() {
        return "getLong";
    }

    public String getJdbcSetterMethodName() {
        return "setLong";
    }

    public String getRandomValue() {
        int max = 100;
        return new Random().nextInt(max) + "L";
    }
}
