package engine.descriptors;

public class BitDescriptor extends TypeDescriptor {
    private final int size;

    public BitDescriptor(int size) {
        this.size = size;

        if (size != 1) {
            throw new IllegalArgumentException("BIT size must be 1");
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public String getJavaTypeName() {
        return "boolean";
    }

    @Override
    public String getJdbcGetterMethodName() {
        return "getBoolean";
    }

    @Override
    public String getJdbcSetterMethodName() {
        return "setBoolean";
    }

    @Override
    public String getRandomValue() {
        return Math.random() > 0.5 ? "true" : "false";
    }
}
