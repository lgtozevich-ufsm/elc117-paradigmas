package engine;

public class TemplateUtils {
    public static String escapeString(String value) {
        return "\"" + value.replace("\"", "\\\"") + "\"";
    }
    
    public static String escapeColumnName(String value) {
        return "`" + value.replace("\"", "\\\"") + "`";
    }

    public static String convertCamelCaseToPascalCase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
