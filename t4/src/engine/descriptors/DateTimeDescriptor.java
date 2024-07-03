package engine.descriptors;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public class DateTimeDescriptor extends TypeDescriptor {
    @Override
    public String getSQLTypeName() {
        return "DATETIME";
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
        Random random = new Random();
        int year = random.nextInt(124) + 1900;

        int month = random.nextInt(12) + 1;

        int day = random.nextInt(28) + 1;

        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        int millisecond = random.nextInt(1000);

        LocalDateTime randomDateTime = LocalDateTime.of(year, month, day, hour, minute, second,
                millisecond * 1_000_000);
        Timestamp randomTimestamp = Timestamp.valueOf(randomDateTime);

        return "new java.sql.Timestamp(" + randomTimestamp.getTime() + "L)";
    }
}
