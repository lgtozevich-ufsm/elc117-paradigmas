package engine.descriptors;

import java.util.Random;

public class TimeDescriptor extends TypeDescriptor {
    @Override
    public String getJavaTypeName() {
        return "java.sql.Time";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getTime";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setTime";
    }

    @Override
    public String getRandomValue() {
        Random random = new Random();

        long ms = Math.abs(random.nextLong());
        return "new java.sql.Time(" + String.valueOf(ms) + "L)";
    
    }
}
