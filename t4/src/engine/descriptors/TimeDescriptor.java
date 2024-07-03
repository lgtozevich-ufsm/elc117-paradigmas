package engine.descriptors;

import java.sql.Time;
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
        int millisInDay = 24 * 60 * 60 * 1000;
        Time time = new Time((long) random.nextInt(millisInDay));
        return "new java.sql.Time(" + time.toString() + ")";
    }
}
