package engine.descriptors;

import java.util.Random;

import engine.TemplateUtils;

public class DecimalDescriptor extends TypeDescriptor {
    private final int size;
    private final int decimalDigits;

    public DecimalDescriptor(int size, int decimalDigits) {
        this.size = size;
        this.decimalDigits = decimalDigits;
    }

    public int getSize() {
        return size;
    }

    public int getDecimalDigits() {
        return decimalDigits;
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

        int integerPart = random.nextInt(0, (int)Math.pow(10, size - decimalDigits) - 1);
        int decimalPart = random.nextInt(0, (int)Math.pow(10, decimalDigits) - 1);

        return "new java.math.BigDecimal(" + TemplateUtils.escapeString(integerPart + "." + decimalPart) + ")";
    }
}
