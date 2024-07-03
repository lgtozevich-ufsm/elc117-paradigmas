package engine.descriptors;
import java.util.Random;
public class LongTextDescriptor extends TypeDescriptor {

    private static final int DEFAULT_TEXT_LENGTH = 200;

    @Override
    public String getSQLTypeName() {
        return "LONGTEXT";
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

        int length = DEFAULT_TEXT_LENGTH;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";

        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
