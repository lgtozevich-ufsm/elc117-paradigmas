package engine.descriptors;

import java.util.Random;
import java.time.LocalDate;

public class DateDescriptor extends TypeDescriptor {
    @Override
    public String getSQLTypeName() {
        return "DATE";
    }

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

        long minDay = LocalDate.of(1900, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2023, 12, 31).toEpochDay();
        long randomDay = minDay + random.nextInt((int) (maxDay - minDay));

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        return "new java.sql.Date(" + randomDate.getYear() + ", " + randomDate.getMonthValue() + ", "
                + randomDate.getDayOfMonth() + ")";
    }
}
