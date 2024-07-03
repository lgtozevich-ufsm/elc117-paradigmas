package engine.descriptors;

public class CharDescriptor extends TypeDescriptor {
    private final int size;

    public CharDescriptor(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String getSQLTypeName() {
        return "CHAR";
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
        return "\"Random\"";
    }
}
