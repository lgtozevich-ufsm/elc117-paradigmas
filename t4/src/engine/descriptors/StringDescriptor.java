package engine.descriptors;

import java.util.Random;
import engine.TemplateUtils;

public class StringDescriptor extends TypeDescriptor {
    private final int size;

    public StringDescriptor(int size) {
        this.size = size;
    }

    @Override
    public String getJavaTypeName() {
        return "String";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getString";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setString";
    }

    @Override
    public String getRandomValue() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";

        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        int length = random.nextInt(Math.min(size, 16)) + 1;

        for (int i = 0; i < length; i++) {
            builder.append(characters.charAt(random.nextInt(characters.length())));
        }

        return TemplateUtils.escapeString(builder.toString());
    }
}
