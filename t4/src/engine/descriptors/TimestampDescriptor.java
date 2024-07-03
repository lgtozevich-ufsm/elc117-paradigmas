package engine.descriptors;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Random;

public class TimestampDescriptor extends TypeDescriptor {
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
        Random random = new Random();

        int year = 1970 + random.nextInt(50);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);

        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        int nanosecond = random.nextInt(1000000);

        LocalDateTime localDateTIme = LocalDateTime.of(year, month, day, hour, minute, second, nanosecond);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTIme, ZonedDateTime.now().getZone());

        return "new java.sql.Timestamp(" + zonedDateTime.toInstant().toEpochMilli() + "L)";
    }
}
