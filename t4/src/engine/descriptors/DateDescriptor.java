package engine.descriptors;

import java.util.Random;

public class DateDescriptor extends TypeDescriptor {
    @Override
    public String getJavaTypeName() {
        return "java.sql.Date";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getDate";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setDate";
    }

    @Override
    public String getRandomValue() {
        Random random = new Random();

        long ms = Math.abs(random.nextLong());
        return "new java.sql.Date(" + String.valueOf(ms) + "L)";
    }
}
