package engine.descriptors;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Random;

public class TimeDescriptor extends TypeDescriptor {
    @Override
    public String getSQLTypeName() {
        return "TIME";
    }

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

        int hour = random.nextInt(24); 
        int minute = random.nextInt(60); 
        int second = random.nextInt(60); 
 
        LocalTime randomTime = LocalTime.of(hour, minute, second);
        Time randomSqlTime = Time.valueOf(randomTime);

        return randomSqlTime.toString();
    }
}
