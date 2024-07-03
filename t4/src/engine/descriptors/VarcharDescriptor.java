package engine.descriptors;

public class VarcharDescriptor extends TypeDescriptor {
    private final int size;

    public VarcharDescriptor(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String getSQLTypeName() {
        return "VARCHAR";
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
        int length = this.size;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(characters.charAt((int) (Math.random() * characters.length())));
        }
        return builder.toString();
    }
}
