package engine.descriptors;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimestampDescriptor extends TypeDescriptor {
    @Override
    public String getSQLTypeName() {
        return "TIMESTAMP";
    }

    @Override
    public String getJavaTypeName() {
        return "java.sql.Timestamp";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getTimestamp";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setTimestamp";
    }

    @Override
    public String getRandomValue() {

        LocalDateTime start = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        long days = (long) (Math.random() * 365 * 50);
        long seconds = (long) (Math.random() * 24 * 60 * 60);
        LocalDateTime randomDateTime = start.plusDays(days).plusSeconds(seconds);

        Timestamp randomTimestamp = Timestamp.valueOf(randomDateTime);
        return "new java.sql.Timestamp(" + randomTimestamp.getTime() + "L)";

    }
}
