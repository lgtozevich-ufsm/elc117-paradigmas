package engine.descriptors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class DecimalDescriptor extends TypeDescriptor {
    private final int size;

    public DecimalDescriptor(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String getJavaTypeName() {
        return "java.math.BigDecimal";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getBigDecimal";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setBigDecimal";
    }

    @Override
    public String getRandomValue() {

        Random random = new Random();

        BigDecimal randomDecimal = new BigDecimal(random.nextDouble() * 1000);

        randomDecimal = randomDecimal.setScale(size, RoundingMode.HALF_UP);

        return "new java.math.BigDecimal(\"" + randomDecimal.toString() + "\")";
    }
}
