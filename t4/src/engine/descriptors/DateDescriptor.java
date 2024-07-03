package engine.descriptors;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

        int year = 1970 + random.nextInt(50);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);

        LocalDateTime localDateTIme = LocalDateTime.of(year, month, day, 0, 0, 0);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTIme, ZonedDateTime.now().getZone());

        return "new java.sql.Date(" + zonedDateTime.toInstant().toEpochMilli() + "L)";
    }
}
