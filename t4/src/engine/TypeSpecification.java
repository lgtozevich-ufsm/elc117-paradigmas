package engine;

public enum TypeSpecification {
    INTEGER {
        @Override
        public String getDatabaseTypeName() {
            return "INT";
        }

        @Override
        public String getJavaTypeName() {
            return "int";
        }

        @Override
        public String getJdbcGetterMethodName() {
            return "getInt";
        }

        @Override
        public String getJdbcSetterMethodName() {
            return "setInt";
        }

        @Override
        public String getRandomValue() {
            int minimum = 1;
            int maximum = 1000;

            int value = minimum + (int)Math.floor(Math.random() * (maximum - minimum));

            return String.valueOf(value);
        }
    };

    public abstract String getDatabaseTypeName();
    public abstract String getJavaTypeName();
    public abstract String getJdbcGetterMethodName();
    public abstract String getJdbcSetterMethodName();
    public abstract String getRandomValue();

    public static TypeSpecification fromDatabaseTypeName(String databaseTypeName) {
        for (TypeSpecification type : values()) {
            if (type.getDatabaseTypeName().equalsIgnoreCase(databaseTypeName)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown database type name: " + databaseTypeName);
    }
}
